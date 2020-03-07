package cn.edu.nbut.InstantMessagingServer.mybatis.mapper;

import cn.edu.nbut.InstantMessagingServer.mybatis.pojo.Contact;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author SuperMaskv
 * <p>
 * contact表相关操作Mapper
 */
public interface ContactMapper {
    //添加联系人
    int addContact(Contact contact);

    //判断联系人记录是否存在
    int isContactExist(@Param("userName") String userName, @Param("contactName") String contactName);

    //删除联系人
    int removeContact(@Param("userName") String userName, @Param("contactName") String contactName);

    //修改联系人备注
    int editContactAlias(Contact contact);

    //返回联系人列表
    List<Contact> getContactList(@Param("userName") String userName);

    //返回单个联系人
    Contact getContact(@Param("userName") String userName, @Param("contactName") String contactName);
}
