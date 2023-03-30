import com.luckk.lizzie.SessionServer;
import io.netty.channel.Channel;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

/**
 * @Author liukun.inspire
 * @Date 2023/3/30 16:12
 * @PackageName: PACKAGE_NAME
 * @ClassName: BoostrapTest
 * @Version 1.0
 */
public class BoostrapTest {
    private final Logger logger = LoggerFactory.getLogger(BoostrapTest.class);

    private CountDownLatch countDownLatch = new CountDownLatch(1);
    @Test
    public void boostrapSessionServer() throws ExecutionException, InterruptedException {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 1, 1000, TimeUnit.SECONDS, new ArrayBlockingQueue<>(100));

        Future<Channel> future = executor.submit(new SessionServer());
        Channel channel = future.get();

        if (channel == null){
            System.out.println("服务启动失败");
        }


        while (! channel.isActive()){
            logger.info(" netty activing.....");
            Thread.sleep(5000);
        }

        logger.info("服务启动成功");
        // countDownLatch.await();

        Thread.sleep(Long.MAX_VALUE);
    }
}
