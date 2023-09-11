package com.sepim.springboot.service.serviceimpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sepim.springboot.entity.CzpUser;
import com.sepim.springboot.entity.ResultMessage;
import com.sepim.springboot.entity.vo.CzpUserVO;
import com.sepim.springboot.mapper.CzpGroupMapper;
import com.sepim.springboot.mapper.CzpUserMapper;
import com.sepim.springboot.service.CzpUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author SorrowPhage
 * @since 2023-09-06
 */
@Service
@RequiredArgsConstructor
public class CzpUserServiceImpl extends ServiceImpl<CzpUserMapper, CzpUser> implements CzpUserService {

    private final CzpUserMapper czpUserMapper;

    private final CzpGroupMapper czpGroupMapper;

    @Override
    public ResultMessage getRelationChat(Map<String, String> param) {
        //找出所有的创始人
        List<String> createrList = czpGroupMapper.getCreater(param);
        //全部列表
        List<CzpUserVO> dataList = czpUserMapper.relationChat2Area(param);
        //寻找配偶
        for (CzpUserVO czpUserVO : dataList) {
            if (czpUserVO.getMateId()!=null) {
                QueryWrapper<CzpUser> wrapper = new QueryWrapper<>();
                wrapper.eq("id", czpUserVO.getMateId());
                czpUserVO.setMate(this.getOne(wrapper));
            }
        }

        Map<String, List<CzpUserVO>> dateMap = dataList.stream().filter(node -> !createrList.contains(node.getId()))
                .collect(Collectors.groupingBy(CzpUserVO::getParentId));

        dataList.forEach(node -> node.setChildren(dateMap.get(node.getId())));

        List<CzpUserVO> collect = dataList.stream().filter(node -> createrList.contains(node.getId())).collect(Collectors.toList());

        //源头不止一个需要封装一个根源
        CzpUserVO smallGroup = CzpUserVO.builder().name(param.get("smallGroup")).children(collect).build();

        return ResultMessage.success(smallGroup);
    }

    @Override
    public ResultMessage getRelationChat2All(Map<String, String> param) {
        //找出所有族群
        List<String> idList = czpGroupMapper.getIdList(param);
        //找出最开始的创始人
        List<String> creaters = czpGroupMapper.getCreater(param);
        //找出所有的数据
        List<CzpUserVO> list = czpUserMapper.relationChat2All(idList);
        //寻找配偶
        for (CzpUserVO czpUserVO : list) {
            if (czpUserVO.getMateId()!=null) {
                QueryWrapper<CzpUser> wrapper = new QueryWrapper<>();
                wrapper.eq("id", czpUserVO.getMateId());
                czpUserVO.setMate(this.getOne(wrapper));
            }
        }
        //转换成树型结构
        Map<String, List<CzpUserVO>> dataMap = list.stream().filter(node -> !creaters.contains(node.getId()))
                .collect(Collectors.groupingBy(CzpUserVO::getParentId));
        list.forEach(node -> node.setChildren(dataMap.get(node.getId())));
        List<CzpUserVO> collect = list.stream().filter(node -> creaters.contains(node.getId())).collect(Collectors.toList());

        //树型结构可能有多个源头，需要在封装一个源节点
        CzpUserVO czpUserVO = CzpUserVO.builder().name(param.get("smallGroup")).children(collect).build();

        return ResultMessage.success(czpUserVO);
    }

    @Override
    public ResultMessage getRelationChatList2Tribe(Map<String, String> param) {
        //没用不打算写，可能需要将相关表删除
        List<CzpUserVO> dataList = czpUserMapper.relationChat2Tribe(param);
        return null;
    }


}
