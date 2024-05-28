package com.currency.service.services;

import com.currency.service.models.Currency;
import com.currency.service.repositories.CurrencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import org.w3c.dom.Element;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


@Service
public class CurrencyService {
    @Autowired
    CurrencyRepository currencyRepository;

    private static final String CBR_DAILY_URL = "https://www.cbr.ru/scripts/XML_daily.asp?date_req=";

    /**
     * Метод для получения курса валюты на указанную дату
     * @param date - дата, на которую требуется получить курс валют
     */
    public void addCurrencyRateToDb(LocalDate date) {
        String url = CBR_DAILY_URL + date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        /*Получение ответа с курсами валют в формате XML*/
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        /*Если пришел код 200 ОК - извлечение кодов валют с их курсом*/
        if (response.getStatusCode() == HttpStatus.OK) {
            try {
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();

                InputSource source = new InputSource(new StringReader(response.getBody()));
                Document document = builder.parse(source);

                NodeList valutes = document.getElementsByTagName("Valute");

                for (int i = 0; i < valutes.getLength(); i++) {
                    Node node = valutes.item(i);

                    if (node.getNodeType() == Node.ELEMENT_NODE) {
                        Element elem = (Element) node;

                        String currencyCode = elem.getElementsByTagName("CharCode")
                                .item(0)
                                .getTextContent();

                        double currencyRate = Double.parseDouble(elem.getElementsByTagName("Value")
                                .item(0)
                                .getTextContent()
                                .replace(",", "."));

                        Currency currency = new Currency(date, currencyRate, currencyCode);

                        /*Проверка на дубликат записи в БД
                          Если курс текущей валюты на заданную дату уже есть в БД, то сохранять дубликат не нужно*/
                        Currency duplicate = currencyRepository.findCurrencyByCodeAndDate(currencyCode, date);
                        if(duplicate == null)
                            currencyRepository.save(currency);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Error: Не удалось получить данные");
        }


    }
}