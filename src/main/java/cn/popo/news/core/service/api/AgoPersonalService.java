package cn.popo.news.core.service.api;


import cn.popo.news.core.dto.PageDTO;
import cn.popo.news.core.dto.api.*;
import cn.popo.news.core.entity.common.Type_info;
import cn.popo.news.core.entity.form.DynamicReportForm;
import cn.popo.news.core.entity.param.PersonalParam;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AgoPersonalService {

    void saveDynamic(String userId,String content,String imgUrl,String videoUrl);

    PageDTO<DynamicVO> findAllDynamicByUserId(Pageable pageable,String userId);


    PageDTO<LookVO> findSixBrowsingHistory(Pageable pageable, String userId);

    PersonalVO findUserInfoByUserId(String userId);

    void updateUserInfo(PersonalParam personalParam);

    void dynamicPraise(String userId,String dynamicId);

    PageDTO<Author> findUserByUserTypeAndNickNameLike(Pageable pageable, String userType, String content);

    void saveCommunication(String uid,String userId,String sendMessage);

    PageDTO<PrivateLetterVO> findUserCommunication(Pageable pageable,String uid,String userId);

    void updateCommunicationLookState(String uid,String userId);

    void dynamicReportSave(DynamicReportForm dynamicReportForm);

    String DefaultUser();

    String addDefaultUser(String userName,String userImg);

    List<Type_info> findAllType();

//    PageDTO<DynamicVO> findAllDynamic(Pageable pageable);

}
