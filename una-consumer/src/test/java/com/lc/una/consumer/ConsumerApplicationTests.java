package com.lc.una.consumer;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Properties;
import java.util.concurrent.Executor;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.PropertyKeyConst;

@SpringBootTest
class ConsumerApplicationTests {

	@Test
	void contextLoads() {

	}

	@Test
	void testunadev() throws InterruptedException, NacosException {
		String serverAddr = "localhost:8848";
		String dataId = "u-na-test-dev.yml";
		String group = "dev";
		String namespace = "dev";
		Properties properties = new Properties();
		properties.put(PropertyKeyConst.SERVER_ADDR, serverAddr);
		properties.put(PropertyKeyConst.NAMESPACE, namespace);
		ConfigService configService = NacosFactory.createConfigService(properties);
		String content = configService.getConfig(dataId, group, 5000);
		System.out.println(content);
		configService.addListener(dataId, group, new Listener() {
			@Override
			public void receiveConfigInfo(String configInfo) {
				System.out.println("recieve:" + configInfo);
			}

			@Override
			public Executor getExecutor() {
				return null;
			}
		});

		boolean isPublishOk = configService.publishConfig(dataId, group, "test:content");
		System.out.println(isPublishOk);

		Thread.sleep(3000);
		content = configService.getConfig(dataId, group, 5000);
		System.out.println(content);

//		boolean isRemoveOk = configService.removeConfig(dataId, group);
//		System.out.println(isRemoveOk);
//		Thread.sleep(3000);

		content = configService.getConfig(dataId, group, 5000);
		System.out.println(content);
		Thread.sleep(300000);
	}
}
