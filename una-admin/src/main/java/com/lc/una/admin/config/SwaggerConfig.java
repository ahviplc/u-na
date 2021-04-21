package com.lc.una.admin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

import java.util.ArrayList;

/**
 * una-admin 的 SwaggerConfig
 * <p>
 * ip:port/doc.html 即【http://localhost:5000/doc.html】
 * 账号密码即见配置文件
 *
 * @author LC
 * @date 2021年4月20日11:26:02
 */
@Configuration
@EnableSwagger2WebMvc
public class SwaggerConfig {

	//配置swagger的Docket的bean实例
	@Bean
	public Docket docket() {
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(apiInfo())
				.select()
				//注意basePackage改成自己每个项目的路径
				.apis(RequestHandlerSelectors.basePackage("com.lc.una.admin"))
				.paths(PathSelectors.any())
				.build();
	}

	//配置swagger信息
	private ApiInfo apiInfo() {
		Contact contact = new Contact("LC", "https://github.com/ahviplc/u-na", "ahlc@sina.cn");
		return new ApiInfo("u-na接口文档",
				"u-na Api Documentation",
				"1.0",
				"http://oneplusone.vip",
				contact,
				"Apache 2.0",
				"http://www.apache.org/licenses/LICENSE-2.0",
				new ArrayList<VendorExtension>()
		);
	}
}
