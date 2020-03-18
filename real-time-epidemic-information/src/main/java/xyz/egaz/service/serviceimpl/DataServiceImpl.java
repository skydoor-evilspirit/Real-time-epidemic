package xyz.egaz.service.serviceimpl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import xyz.egaz.bean.DataBean;
import xyz.egaz.mapper.DataMapper;
import xyz.egaz.service.DataService;

@Service
public class DataServiceImpl extends ServiceImpl<DataMapper, DataBean> implements DataService {
}
