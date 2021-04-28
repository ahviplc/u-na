package com.lc.una.admin.annotion.AuthorityVerify;

import cn.hutool.log.StaticLog;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

// todo 等待具体完善

/**
 * 权限校验 切面实现
 *
 * @author : LC
 * @date : 2021年4月28日10:20:53
 */
@Aspect
@Component
public class AuthorityVerifyAspect {

	// 切入点：已经被增强的连接点。例如：addUser()
	// 这里是匹配注解 @AuthorityVerify
	@Pointcut(value = "@annotation(authorityVerify)")
	public void pointcut(AuthorityVerify authorityVerify) {

	}

	// 可在此加入JoinPoint打印切点信息
	// 前置通知:在我们执行目标方法之前运行(@Before)
	@Before("pointcut(authorityVerify)")
	public void doBefore(JoinPoint joinPoint, AuthorityVerify authorityVerify) {
		StaticLog.info("------@AuthorityVerify【前置通知】------{}", joinPoint);
		// @AuthorityVerify(value = "LC") 获取注解外挂的值
		StaticLog.info("------@AuthorityVerify【前置通知】------获取注解外挂的值【authorityVerify.value()】 => {}", authorityVerify.value());
	}

	// 后置通知:在我们目标方法运行结束之后 ,不管有没有异常(@After)
	@After("pointcut(authorityVerify)")
	public void doAfter(JoinPoint joinPoint, AuthorityVerify authorityVerify) {
		StaticLog.info("------@AuthorityVerify【后置通知】------{}", joinPoint);
	}

	// 返回通知:在我们的目标方法正常返回值后运行(@AfterReturning)
	@AfterReturning(value = "pointcut(authorityVerify)", returning = "returningResultData")
	public void doAfterReturning(JoinPoint joinPoint, AuthorityVerify authorityVerify, Object returningResultData) {
		StaticLog.info("------@AuthorityVerify【返回通知】------{}------获取目标方法的返回值(数据)【returning】=> {}", joinPoint, returningResultData);
	}

	// 异常通知:在我们的目标方法出现异常后运行(@AfterThrowing)
	@AfterThrowing(value = "pointcut(authorityVerify)", throwing = "e")
	public void doAfterThrowing(JoinPoint joinPoint, AuthorityVerify authorityVerify, Throwable e) {
		StaticLog.info("------@AuthorityVerify【异常通知】------{}------【异常信息】=> {}", joinPoint, e);
	}

	// 环绕通知:动态代理, 需要手动执行joinPoint.procced()(其实就是执行我们的目标方法执行之前相当于前置通知, 执行之后就相当于我们后置通知(@Around)
	@Around(value = "pointcut(authorityVerify)")
	public Object doAround(ProceedingJoinPoint joinPoint, AuthorityVerify authorityVerify) throws Throwable {
		ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = requestAttributes.getRequest();
		// 获取请求路径
		String url = request.getRequestURI();
		StaticLog.info("------@AuthorityVerify【环绕前通知】------url => {}", url);
		StaticLog.info("------@AuthorityVerify【环绕前通知】------{}", joinPoint);
		// 执行业务 执行目标方法
		Object proceedObj = joinPoint.proceed();
		StaticLog.info("------@AuthorityVerify【环绕后通知】------{}", joinPoint);
		return proceedObj;
	}
}
