package com.luckk.lizzie.bind;

import net.sf.cglib.core.Signature;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.InterfaceMaker;
import org.apache.dubbo.rpc.service.GenericService;
import org.objectweb.asm.Type;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author liukun.inspire
 * @Date 2023/11/21 15:23
 * @PackageName: com.luckk.lizzie.bind
 * @ClassName: GenericReferenceProxyFactory
 * @Version 1.0
 */
public class GenericReferenceProxyFactory {

    private GenericService genericService;

    private Map<String, IGenericReference> genericReferenceCache = new HashMap<>();

    public GenericReferenceProxyFactory(GenericService genericService) {
        this.genericService = genericService;
    }


    /**
     * 这里就需要真实的去创建代理的工作了，
     *
     * @param method
     */
    public IGenericReference newInstance(String method) {
        // 如果存在则直接返回
        return genericReferenceCache.computeIfAbsent(method, k -> {
            GenericReferenceProxy genericReferenceProxy = new GenericReferenceProxy(genericService, method);
            InterfaceMaker interfaceMaker = new InterfaceMaker();
            interfaceMaker.add(new Signature(method, Type.getType(String.class), new Type[]{Type.getType(String.class)}), null);
            Class interfaceClass = interfaceMaker.create();
            Enhancer enhancer = new Enhancer();
            enhancer.setSuperclass(Object.class);
            enhancer.setInterfaces(new Class[]{IGenericReference.class, interfaceClass});
            enhancer.setCallback(genericReferenceProxy);
            return (IGenericReference) enhancer.create();
        });
    }
}
