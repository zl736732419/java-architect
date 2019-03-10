package com.zheng.jvm.hotloading;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;

public class HelloMain {
	private URLClassLoader classLoader;
	private Object worker;
	private long lastTime;
	private String classDir="E:\\workspace\\java-architect\\target\\classes";
	public static void main(String[] args) throws Exception {
		HelloMain helloMain=new HelloMain();
		helloMain.execute();
	}
	
	private void execute() throws Exception {
		while(true){
			//监测是否需要加载
			if(checkIsNeedLoad()){
				System.out.println("检测到新版本，准备重新加载");
				reload();
				System.out.println("重新加载完成");
			}
			//一秒
			invokeMethod();
			Thread.sleep(1000);
			
		}
	}
	
	private void invokeMethod() throws Exception {
		//通过反射方式调用
		//使用反射的主要原因是：不想Work被appclassloader加载，
//		如果被appclassloader加载的话，再通过自定义加载器加载会有点问题
//		Method method=worker.getClass().getDeclaredMethod("sayHello", null);
//		method.invoke(worker, null);
        ((Worker) worker).sayHello();
	}

	private void reload() throws Exception {
		classLoader = new MyClassLoader(new URL[] { new URL(
				"file:"+classDir)});
		worker =  classLoader.loadClass("com.zheng.jvm.hotloading.Worker")
				.newInstance();
		
	}

	private boolean checkIsNeedLoad() {
		File file=new File(classDir+"\\com\\zheng\\jvm\\hotloading\\Worker.class");
		long newTime=file.lastModified();
		if(lastTime<newTime || newTime == 0l){
			lastTime=newTime;
			return true;
		}
		return false;
	}

	
}
