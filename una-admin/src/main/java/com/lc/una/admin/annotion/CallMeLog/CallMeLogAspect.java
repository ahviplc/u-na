package com.lc.una.admin.annotion.CallMeLog;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import cn.hutool.log.StaticLog;
import com.alibaba.csp.sentinel.util.StringUtil;
// import com.lc.una.utils.tools.AspectUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * 标注该注解的方法需要记录调用我详细相关信息 切面实现
 * 记录的请求内容如下:
 * 请求时间
 * 请求IP
 * 请求Host
 * 请求端口Port
 * 请求地址uri
 * 请求方法
 * 请求参数
 * 请求头
 * 请求类型
 * 请求token
 * 请求类方法签名
 * 请求类方法签名的名称
 * 请求类方法参数
 * 对应的类名 方法名
 * 请求url
 * 执行请求时长
 *
 * @author : LC
 * @date : 2021年4月28日13:42:39
 */
@Aspect
@Component
public class CallMeLogAspect {

	// 访问开始时间
	private Long startVisitTime;

	// 访问的类
	private Class visitClass;

	// 切入点：已经被增强的连接点。例如：addUser()
	// 这里是匹配注解 @CallMeLog
	@Pointcut(value = "@annotation(callMeLog)")
	public void pointcut(CallMeLog callMeLog) {

	}

	// 可在此加入JoinPoint打印切点信息
	// 前置通知:在我们执行目标方法之前运行(@Before)
	@Before("pointcut(callMeLog)")
	public void doBefore(JoinPoint joinPoint, CallMeLog callMeLog) throws NoSuchMethodException {
		StaticLog.info("------@CallMeLog【前置通知】------{}", joinPoint);
		// @CallMeLog(value = "我是值") 获取注解外挂的值
		StaticLog.info("------@CallMeLog【前置通知】------获取注解外挂的值【callMeLog.value() callMeLog.platform()】 => {} {}", callMeLog.value(), callMeLog.platform());
		// 开始此切面实际的逻辑
		startVisitTime = DateUtil.current();
		// 具体要访问的类
		visitClass = joinPoint.getTarget().getClass();
		// 获取访问方法签名
		Signature signature = joinPoint.getSignature();
		// 获取访问方法签名的名称
		String methodNameOfSignature = signature.getName();
		// 获取访问方法的参数
		Object[] args = joinPoint.getArgs();

		// 使用AspectUtil工具类
		// 获取具体执行方法的Method对象 访问的方法 获得 Method
		// Method visitMethod = AspectUtil.INSTANCE.getMethod(joinPoint);
		// System.out.println(visitMethod.getName());
		// 获取的和下面一样

		// 获取具体执行方法的Method对象 访问的方法
		Method visitMethod;
		MethodSignature msig = (MethodSignature) signature;
		visitMethod = visitClass.getMethod(methodNameOfSignature, msig.getParameterTypes());
		// 打印参数类型
		// System.out.println(visitMethod.getParameterTypes());

		// 开始处理请求内容
		ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = requestAttributes.getRequest();
		String requestMethod = request.getMethod();
		// 处理 headerNames
		Enumeration<String> headerNames = request.getHeaderNames();
		Map<String, Object> headerMap = new HashMap<>(10);
		do {
			String header = headerNames.nextElement();
			headerMap.put(header, request.getHeader(header));
		} while (headerNames.hasMoreElements());

		// 处理 token
		String token = request.getParameter("token");
		if (StrUtil.isEmpty(token)) {
			token = request.getHeader("token");
			if (StringUtil.isEmpty(token)) {
				// 也可从cookie中获取 这里给空字符串
				token = "null";
			}
		}

		StaticLog.info("------@CallMeLog【前置通知】------请求内容 开始------");
		StaticLog.info("------@CallMeLog【前置通知】------请求时间: {}", DateUtil.date(startVisitTime).toString("yyyy-MM-dd HH:mm:ss"));
		StaticLog.info("------@CallMeLog【前置通知】------请求IP: {}", request.getRemoteAddr());
		StaticLog.info("------@CallMeLog【前置通知】------请求Host: {}", request.getRemoteHost());
		StaticLog.info("------@CallMeLog【前置通知】------请求端口Port: {}", request.getRemotePort());
		StaticLog.info("------@CallMeLog【前置通知】------请求地址uri: {}", request.getRequestURI());
		StaticLog.info("------@CallMeLog【前置通知】------请求方法: {}", requestMethod);
		StaticLog.info("------@CallMeLog【前置通知】------请求参数: {}", JSONUtil.toJsonStr(request.getParameterMap()));
		StaticLog.info("------@CallMeLog【前置通知】------请求头: {}", JSONUtil.toJsonStr(headerMap));
		StaticLog.info("------@CallMeLog【前置通知】------请求类型: {}", request.getContentType());
		StaticLog.info("------@CallMeLog【前置通知】------请求token: {}", token);
		StaticLog.info("------@CallMeLog【前置通知】------请求类方法签名: {}", signature);
		StaticLog.info("------@CallMeLog【前置通知】------请求类方法签名的名称: {}", methodNameOfSignature);
		StaticLog.info("------@CallMeLog【前置通知】------请求类方法参数: {}", ArrayUtil.toString(args));
		StaticLog.info("------@CallMeLog【前置通知】------对应的类名: {} 方法名: {}", visitClass.getName(), methodNameOfSignature);

		// 处理url
		if (ObjectUtil.isNotNull(visitClass) && ObjectUtil.isNotNull(visitMethod)) {
			// 获取RequestMapping注解
			// java/lang/Class.java:3412
			// Returns this element's annotation.
			RequestMapping requestMappingClassAnnotation = (RequestMapping) visitClass.getAnnotation(RequestMapping.class);
			if (ObjectUtil.isNotNull(requestMappingClassAnnotation)) {
				String[] classValue = requestMappingClassAnnotation.value();
				if ("GET".equals(requestMethod)) {
					// 获取GetMapping注解
					// java/lang/reflect/Method.java:621
					// Returns this element's annotation for the specified type if such an annotation is present, else null.
					GetMapping visitMethodAnnotation = visitMethod.getAnnotation(GetMapping.class);
					getRealURL(classValue, visitMethodAnnotation != null, visitMethodAnnotation.value());
				} else if ("POST".equals(requestMethod)) {
					// 获取PostMapping注解
					// java/lang/reflect/Method.java:621
					// Returns this element's annotation for the specified type if such an annotation is present, else null.
					PostMapping visitMethodAnnotation = visitMethod.getAnnotation(PostMapping.class);
					getRealURL(classValue, visitMethodAnnotation != null, visitMethodAnnotation.value());
				} else {
					StaticLog.info("------@CallMeLog【前置通知】------未定义的请求方式{} 获取url跳出 不处理------", requestMethod);
				}
			}
		}

		StaticLog.info("------@CallMeLog【前置通知】------请求内容 结束------");
	}

	// 后置通知:在我们目标方法运行结束之后 ,不管有没有异常(@After)
	@After("pointcut(callMeLog)")
	public void doAfter(JoinPoint joinPoint, CallMeLog callMeLog) {
		StaticLog.info("------@CallMeLog【后置通知】------{}", joinPoint);
	}

	// 返回通知:在我们的目标方法正常返回值后运行(@AfterReturning)
	@AfterReturning(value = "pointcut(callMeLog)", pointcut = "pointcut(callMeLog)", returning = "returningResultData")
	public void doAfterReturning(JoinPoint joinPoint, CallMeLog callMeLog, Object returningResultData) {
		StaticLog.info("------@CallMeLog【返回通知】------{}------获取目标方法的返回值(数据)【returning】=> {}", joinPoint, returningResultData);
		// 开始此切面实际的逻辑
		long allVisitTime = DateUtil.current() - startVisitTime;
		StaticLog.info("------@CallMeLog【返回通知】-----执行请求时长: {}ms", allVisitTime);
		StaticLog.info("------@CallMeLog【返回通知】------返回请求成功的内容 start ------");
		// 已json串格式输出请求成功的内容
		StaticLog.info(JSONUtil.toJsonStr(returningResultData));
		StaticLog.info("------@CallMeLog【返回通知】------返回请求成功的内容 end ------");
	}

	// 异常通知:在我们的目标方法出现异常后运行(@AfterThrowing)
	@AfterThrowing(value = "pointcut(callMeLog)", throwing = "e")
	public void doAfterThrowing(JoinPoint joinPoint, CallMeLog callMeLog, Throwable e) {
		StaticLog.info("------@CallMeLog【异常通知】------{}------【异常信息】=> {}", joinPoint, e);
	}

	// 环绕通知:动态代理, 需要手动执行joinPoint.procced()(其实就是执行我们的目标方法执行之前相当于前置通知, 执行之后就相当于我们后置通知(@Around)
	@Around(value = "pointcut(callMeLog)")
	public Object doAround(ProceedingJoinPoint joinPoint, CallMeLog callMeLog) throws Throwable {
		ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = requestAttributes.getRequest();
		// 获取请求路径
		String url = request.getRequestURI();
		StaticLog.info("------@CallMeLog【环绕前通知】------url => {}", url);
		StaticLog.info("------@CallMeLog【环绕前通知】------{}", joinPoint);
		// 执行业务 执行目标方法
		Object proceedObj = joinPoint.proceed();
		StaticLog.info("------@CallMeLog【环绕后通知】------{}", joinPoint);
		return proceedObj;
	}

	/**
	 * getRealURL 获取url
	 *
	 * @param classValue
	 * @param b
	 * @param value
	 */
	private void getRealURL(String[] classValue, boolean b, String[] value) {
		String url;
		if (b) {
			String[] methodValue = value;
			url = classValue[0] + methodValue[0];
			StaticLog.info("------@CallMeLog【前置通知】------请求url: {}", url);
		}
	}
}
