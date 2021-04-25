package com.lc.una.commons.config.mybatis;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.incrementer.H2KeyGenerator;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
//import com.baomidou.mybatisplus.extension.plugins.PerformanceInterceptor; // 去除了
//import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor; // 过期了
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Mybatis-plus插件配置
 *
 * @author LC
 * @date 2021年4月22日13:40:49
 */
// 这个【@MapperScan】注解，作用相当于下面的@Bean MapperScannerConfigurer
// @MapperScan("com.lc.una.xo.mapper")
// 二者配置其一即可
@Configuration
public class MybatisPlusConfig {

	/**
	 * 相当于顶部的：
	 * {@code @MapperScan("com.baomidou.springboot.mapper*")}
	 * 这里可以扩展，比如使用配置文件来配置扫描Mapper的路径
	 */
	@Bean
	public MapperScannerConfigurer mapperScannerConfigurer() {
		MapperScannerConfigurer scannerConfigurer = new MapperScannerConfigurer();
		// 可以通过环境变量获取你的mapper路径,这样mapper扫描可以通过配置文件配置了
		scannerConfigurer.setBasePackage("com.lc.una.xo.mapper*");
		return scannerConfigurer;
	}

	/**
	 * mybatis-plus分页插件
	 * 文档：http://mp.baomidou.com
	 */
	// 老版本
    /*@Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        return paginationInterceptor;
    }*/
	// 最新版
	@Bean
	public MybatisPlusInterceptor mybatisPlusInterceptor() {
		MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
		interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
		return interceptor;
	}

	// 先用不到
	/*@Bean
	public H2KeyGenerator getH2KeyGenerator() {
		return new H2KeyGenerator();
	}*/

	/**
	 * 性能分析拦截器，不建议生产使用
	 */
    /*@Bean
    public PerformanceInterceptor performanceInterceptor() {
        return new PerformanceInterceptor();
    }*/
}
