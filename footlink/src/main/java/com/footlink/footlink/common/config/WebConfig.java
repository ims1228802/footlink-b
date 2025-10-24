package com.footlink.footlink.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

import com.footlink.footlink.user.login.interceptor.CommonInterceptor;
import com.footlink.footlink.user.login.interceptor.LoginInterceptor;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer{

	// application.properties에서 "C:/home/SideProject/FootLink/attachment/" 값을 읽어옴
	@Value("${file.path}")
	private String fileRealPath; // 변수명은 그대로 두셔도 됩니다.
	
	private final CommonInterceptor commonInterceptor; 
	private final LoginInterceptor loginInterceptor; 
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		
		registry.addInterceptor(commonInterceptor)
				.addPathPatterns("/admin/**");
		
		registry.addInterceptor(loginInterceptor)
				.addPathPatterns("/admin/**")
				.excludePathPatterns("/admin/login")
				.excludePathPatterns("/admin/loginPro")
				.excludePathPatterns("/admin/logout");
				
		WebMvcConfigurer.super.addInterceptors(registry);
	}
	
	// ✅ [수정됨] addResourceHandlers 메서드
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		
		registry.addResourceHandler("/attachment/**") // 1. 브라우저가 요청할 URL: /attachment/
				
				// 2. 실제 파일 위치: "file:" 프리픽스 + application.properties에서 읽어온 경로
				// "file:C:/home/SideProject/FootLink/attachment/" 가 됩니다.
				.addResourceLocations("file:" + fileRealPath) 
				
				.setCachePeriod(3600)
				.resourceChain(true)
				.addResolver(new PathResourceResolver());
		
		WebMvcConfigurer.super.addResourceHandlers(registry);
	}
	
	public String getOSFilePath() {
		String rootPath = "file:///";
		String os = System.getProperty("os.name").toLowerCase();
		
		if(os.contains("win")) {
			rootPath = "file:///c:";
		}else if(os.contains("linux")) {
			rootPath = "file://";
		}else if(os.contains("mac")) {			
			rootPath = "file://";
		}
		
		return rootPath;
	}

}
