package com.luckk.lizzie.config;

import com.luckk.lizzie.bind.GenericReferenceRegistry;
import com.luckk.lizzie.bind.IGenericReference;
import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.rpc.service.GenericService;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author liukun.inspire
 * @Date 2023/11/21 15:11
 * @PackageName: com.luckk.lizzie.config
 * @ClassName: Configuration
 * @Version 1.0
 */
public class Configuration {


    private final GenericReferenceRegistry registry = new GenericReferenceRegistry(this);
    private final Map<String, ApplicationConfig> applicationConfigMap = new HashMap<>();

    private final Map<String, RegistryConfig> registryConfigMap = new HashMap<>();

    private final Map<String, ReferenceConfig<GenericService>> referenceConfigMap = new HashMap<>();

    public Configuration() {
        // TODO 后期从配置中获取
        ApplicationConfig application = new ApplicationConfig();
        application.setName("lizzie-marketing");
        application.setQosEnable(false);

        RegistryConfig registry = new RegistryConfig();
        registry.setAddress("nacos://117.72.41.145:8848");
        registry.setRegister(false);

        ReferenceConfig<GenericService> reference = new ReferenceConfig<>();
        reference.setInterface("com.luckk.lizzie.service.RewardService");
        reference.setVersion("1.0.0");
        reference.setGeneric("true");

        applicationConfigMap.put("lizzie-marketing", application);
        registryConfigMap.put("lizzie-marketing", registry);
        referenceConfigMap.put("com.luckk.lizzie.service.RewardService", reference);
    }


    public ApplicationConfig getApplicationConfig(String application) {
        return applicationConfigMap.get(application);
    }

    public RegistryConfig getRegistryConfig(String application) {
        return registryConfigMap.get(application);
    }

    public ReferenceConfig<GenericService> getReferenceConfig(String interfaceName) {
        return referenceConfigMap.get(interfaceName);
    }

    public Map<String, RegistryConfig> getRegistryConfigMap() {
        return registryConfigMap;
    }

    public Map<String, ReferenceConfig<GenericService>> getReferenceConfigMap() {
        return referenceConfigMap;
    }

    public IGenericReference getGenericService(String methodName) {
        return registry.getGenericReference(methodName);
    }


    public void addGenericReference(String application, String interfaceName, String methodName) {
        registry.addGenericReference(application, interfaceName, methodName);
    }
}
