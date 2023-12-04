package com.luckk.lizzie.bind;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.apache.dubbo.rpc.service.GenericService;

import java.lang.reflect.Method;

/**
 * @Author liukun.inspire
 * 代理类，那么就需要实现具体的代理工作
 * 需要同时代理RPC的接口，以及我们的IGenericReference提供的统一的调用模式
 * 这里应该提前仅仅是采用了String的方式来进行动态代理
 * 这里不去做这么多，这个代理完全就是实际的调用的逻辑？直接执行方法？
 * @Date 2023/11/21 15:17
 * @PackageName: com.luckk.lizzie.bind
 * @ClassName: GenericReferenceProxy
 * @Version 1.0
 */
public class GenericReferenceProxy implements MethodInterceptor {

    private GenericService genericService;

    private String methodName;

    public GenericReferenceProxy(GenericService genericService, String methodName) {
        this.genericService = genericService;
        this.methodName = methodName;
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {

        Class<?>[] parameterTypes = method.getParameterTypes();
        String[] parameters = new String[parameterTypes.length];

        for (int i = 0; i < parameterTypes.length; i++) {
            parameters[i] = parameterTypes[i].getName();
        }
        // 这个这个objects是什么
        return genericService.$invoke(methodName, parameters, objects);
    }
}
