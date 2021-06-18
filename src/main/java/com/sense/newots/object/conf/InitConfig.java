package com.sense.newots.object.conf;

import lombok.extern.slf4j.Slf4j;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

//扫描conf.xml文件的信息
@Slf4j
public class InitConfig {
    public static String PORT;
    public static String BIND_IP;
    public static String DB_URL;
    public static String DB_PWD;
    public static String OUTBOUND_URL;
    public static String PENNY_URL;
    public static String ENVIRONMENT;
    public static String SMS_URL;
    public static String SMS_FAIL;
    public static String MONGODB_URL;
    public static String STRATEGY_URL;

    public static boolean init() throws ParserConfigurationException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        Element theContext = null;
        Element root = null;

        factory.setIgnoringElementContentWhitespace(true);
        DocumentBuilder db = factory.newDocumentBuilder();

        String sp = System.getProperty("file.separator");
        String filePath = "conf.xml";
        Document xmldoc = null;
        try {
            xmldoc = db.parse(InitConfig.class.getClassLoader().getResourceAsStream(filePath));
        } catch (Exception e) {
            log.error(" Server Default Config File  Not Found !");
            return false;
        }
        root = xmldoc.getDocumentElement();
        theContext = (Element) selectSingleNode("/config/variable[@name='port']", root);
        if (theContext != null) {
            PORT = theContext.getTextContent().trim();
        }
        theContext = (Element) selectSingleNode("/config/variable[@name='bind_ip']", root);
        if (theContext != null) {
            BIND_IP = theContext.getTextContent().trim();
        }
        theContext = (Element) selectSingleNode("/config/variable[@name='db_url']", root);
        if (theContext != null) {
            DB_URL = theContext.getTextContent().trim();
        }
        theContext = (Element) selectSingleNode("/config/variable[@name='db_pwd']", root);
        if (theContext != null) {
            DB_PWD = theContext.getTextContent().trim();
        }
        theContext = (Element) selectSingleNode("/config/variable[@name='outbound_url']", root);
        if (theContext != null) {
            OUTBOUND_URL = theContext.getTextContent().trim();
        }
        theContext = (Element) selectSingleNode("/config/variable[@name='environment']", root);
        if (theContext != null) {
            ENVIRONMENT = theContext.getTextContent().trim();
        }
        theContext = (Element) selectSingleNode("/config/variable[@name='penny_url']", root);
        if (theContext != null) {
            PENNY_URL = theContext.getTextContent().trim();
        }
        theContext = (Element) selectSingleNode("/config/variable[@name='sms_url']", root);
        if (theContext != null) {
            SMS_URL = theContext.getTextContent().trim();
        }
        theContext = (Element) selectSingleNode("/config/variable[@name='sms_fail']", root);
        if (theContext != null) {
            SMS_FAIL = theContext.getTextContent().trim();
        }
        theContext = (Element) selectSingleNode("/config/variable[@name='mongodb_url']", root);
        if (theContext != null) {
            MONGODB_URL = theContext.getTextContent().trim();
        }
        theContext = (Element) selectSingleNode("/config/variable[@name='strategy_url']", root);
        if (theContext != null) {
            STRATEGY_URL = theContext.getTextContent().trim();
        }


        log.info("Config File Init Complete !");
        return true;
    }

    public static Node selectSingleNode(String express, Object source) {
        Node result = null;
        XPathFactory xpathFactory = XPathFactory.newInstance();
        XPath xpath = xpathFactory.newXPath();
        try {
            result = (Node) xpath
                    .evaluate(express, source, XPathConstants.NODE);
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
        return result;
    }
}