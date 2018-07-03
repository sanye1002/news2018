package cn.popo.news.core.service.api;


import cn.popo.news.core.dto.PageDTO;
import cn.popo.news.core.dto.api.*;
import cn.popo.news.core.entity.form.DynamicReportForm;
import cn.popo.news.core.entity.param.PersonalParam;
import org.springframework.data.domain.Pageable;

public interface AgoPersonalService {

    void saveDynamic(String userId,String content,String imgUrl);

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

//    PageDTO<DynamicVO> findAllDynamic(Pageable pageable);

}
