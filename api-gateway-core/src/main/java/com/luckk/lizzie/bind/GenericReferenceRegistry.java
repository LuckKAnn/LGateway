package com.luckk.lizzie.bind;

import com.luckk.lizzie.config.Configuration;
import jdk.nashorn.internal.runtime.regexp.joni.Config;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.bootstrap.DubboBootstrap;
import org.apache.dubbo.config.utils.ReferenceConfigCache;
import org.apache.dubbo.rpc.service.GenericService;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author liukun.inspire
 * 启动注册？其实需要启动dubbo远程服务吧
 * 还需要把我们的对应的配置设置到dubbo本身内部里面去
 * @Date 2023/11/21 15:29
 * @PackageName: com.luckk.lizzie.bind
 * @ClassName: GenericReferenceRegistry
 * @Version 1.0
 */
public class GenericReferenceRegistry {

    private Configuration configuration;

    private Map<String, GenericReferenceProxyFactory> knownGenericReferences = new HashMap<>();

    public GenericReferenceRegistry(Configuration configuration) {
        this.configuration = configuration;
    }

    public IGenericReference getGenericReference(String methodName) {
        GenericReferenceProxyFactory genericReferenceProxyFactory = knownGenericReferences.get(methodName);
        if (genericReferenceProxyFactory == null) {
            throw new RuntimeException("Type " + methodName + " is not known to the GenericReferenceRegistry.");
        }
        return genericReferenceProxyFactory.newInstance(methodName);
    }

    public void addGenericReference(String application, String interfaceName, String methodName) {
        // 增加服务？
        ApplicationConfig applicationConfig = configuration.getApplicationConfig(application);
        ReferenceConfig<GenericService> referenceConfig = configuration.getReferenceConfig(interfaceName);
        RegistryConfig registryConfig = configuration.getRegistryConfig(application);

        DubboBootstrap dubboBootstrap = DubboBootstrap.getInstance();
        dubboBootstrap.application(applicationConfig)
                .registry(registryConfig)
                .reference(referenceConfig)
                .start();

        // 这里其实是Dubbo本身的配置
        ReferenceConfigCache cache = ReferenceConfigCache.getCache();
        GenericService genericService = cache.get(referenceConfig);
        knownGenericReferences.put(methodName, new GenericReferenceProxyFactory(genericService));
    }


}
