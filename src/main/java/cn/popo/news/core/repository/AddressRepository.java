package cn.popo.news.core.repository;

import cn.popo.news.core.entity.common.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Integer> {
    Address findAddressByCountryAndRegionAndCityAndCounty(String country,String region,String city,String county);
}
