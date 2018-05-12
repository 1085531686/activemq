package test.learn.consumer;

public class ConsumeService {

	public static void main(String[] args) {
		Consumer consumer =new Consumer("oper","failover:tcp://127.0.0.1:61616");
//		consumer.destroy();
	}
}
