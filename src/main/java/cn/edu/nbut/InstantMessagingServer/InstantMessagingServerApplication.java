package cn.edu.nbut.InstantMessagingServer;

import cn.edu.nbut.InstantMessagingServer.netty.NettyConfigImpl;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@MapperScan(basePackages = "cn.edu.nbut.InstantMessagingServer.mybatis.mapper")
public class InstantMessagingServerApplication implements CommandLineRunner {
    @Autowired
    private NettyConfigImpl config;

    public static void main(String[] args) {
        SpringApplication.run(InstantMessagingServerApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        config.setParentGroup();
        config.setChildGroup();
        config.setChannel(NioServerSocketChannel.class);
        config.setHandler();
        config.bind(9999);
    }
}
