package test.learn.producer;

import javax.jms.JMSException;

import test.learn.dto.Oper;

public class ProduceService {

	public static void main(String[] args) {
		Producer producer =new Producer("oper", "failover:tcp://127.0.0.1:61616");
		
		Oper oper = new Oper();
		oper.setOper("12121");
		try {
			producer.sendObject(oper);
		} catch (Exception e) {
			System.out.println("Êý¾ÝÒì³£");
			e.printStackTrace();
		}
		producer.destroy();
	}
}
