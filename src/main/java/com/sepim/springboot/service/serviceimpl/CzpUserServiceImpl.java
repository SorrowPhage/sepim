package com.sepim.springboot.service.serviceimpl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sepim.springboot.entity.CzpIntoTribeTemp;
import com.sepim.springboot.entity.CzpUser;
import com.sepim.springboot.entity.ResultMessage;
import com.sepim.springboot.entity.excelmodel.CzpUpload;
import com.sepim.springboot.entity.listener.CzpListener;
import com.sepim.springboot.entity.vo.CzpUserVO;
import com.sepim.springboot.mapper.CzpGroupMapper;
import com.sepim.springboot.mapper.CzpUserMapper;
import com.sepim.springboot.service.CzpIntoTribeTempService;
import com.sepim.springboot.service.CzpUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
@Slf4j
@Service
@RequiredArgsConstructor
public class CzpUserServiceImpl extends ServiceImpl<CzpUserMapper, CzpUser> implements CzpUserService {

    private final CzpUserMapper czpUserMapper;

    private final CzpGroupMapper czpGroupMapper;

    private final CzpIntoTribeTempService czpIntoTribeTempService;


    /**
     * 获取zp树型结构数据，这个数据只包括某个smallGroup的
     * @param param smallGroup
     * @return 树型结构
     */
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

        //封装为树型结构
        List<CzpUserVO> czpTree = this.createCzpTree(dataList, createrList);

        //源头不止一个需要封装一个根源
        CzpUserVO smallGroup = CzpUserVO.builder().name(param.get("smallGroup")).children(czpTree).build();

        return ResultMessage.success(smallGroup);
    }


    /**
     * 获取zp树型结构数据，这个还包括下一级的子节点的数据，但是不包括子节点的子节点，所以是有瑕疵的
     * @param param 始源族群编码
     * @return 树型结构
     */
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
        List<CzpUserVO> czpTree = this.createCzpTree(list, creaters);
        //树型结构可能有多个源头，需要在封装一个源节点
        CzpUserVO czpUserVO = CzpUserVO.builder().name(param.get("smallGroup")).children(czpTree).build();

        return ResultMessage.success(czpUserVO);
    }


    /**
     * 获取zp树型结构数据，这个还包括下一级的子节点的数据，包括子节点的子节点(while)，真正的获取所以数据
     * @param param 始源族群编码
     * @return 树型结构数据
     */
    @Override
    public ResultMessage relationChatAll(Map<String, String> param) {
        String smallGroup = param.get("smallGroup");
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

        //找出所有的子节点数据加到dataList里面
        List<String> subgroup = czpGroupMapper.getSubgroup(smallGroup);
        List<CzpUserVO> list = subgroupList(subgroup);
        dataList.addAll(list);

        List<CzpUserVO> czpTree = this.createCzpTree(dataList, createrList);
        //源头不止一个需要封装一个根源
        CzpUserVO resultData = CzpUserVO.builder().name(param.get("smallGroup")).children(czpTree).build();

        return ResultMessage.success(resultData);
    }


    /**
     * 上传Excel文件，
     * @param file excel文件
     * @param userId 操作者id
     * @return 批次号
     */
    @Override
    public ResultMessage uploadCzpData(MultipartFile file, String userId) {
        try {
            //EasyExcel读取文并保存。
            EasyExcel.read(file.getInputStream(), CzpUpload.class, new CzpListener()).sheet() .doRead();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //生成批次号
        String batchNo = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        for (CzpUpload czpUpload : CzpListener.czpCache) {
            //判断父节点用户是否存在
            QueryWrapper<CzpUser> parentWrapper = new QueryWrapper<>();
            parentWrapper.eq("id", czpUpload.getParentId());
            CzpUser two = this.getOne(parentWrapper);
            if (two != null) {
                //判断子节点用户是否存在
                QueryWrapper<CzpUser> wrapper = new QueryWrapper<>();
                wrapper.eq("id", czpUpload.getUserId());
                CzpUser one = this.getOne(wrapper);
                if (one != null) {
                    //用户都存在，将数据保存到临时表中。
                    CzpIntoTribeTemp czpIntoTribeTemp = CzpIntoTribeTemp.builder().userId(czpUpload.getUserId()).parentId(czpUpload.getParentId())
                            .operaterId(userId).operaterTime(LocalDateTime.now().format(
                                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                            .batchNo(batchNo).build();
                    czpIntoTribeTempService.save(czpIntoTribeTemp);
                }
            }
        }
        //删除掉EasyExcel保存的数据，防止生成脏数据
        CzpListener.czpCache.clear();
        return ResultMessage.success(batchNo);
    }


    /**
     * 转换成树型结构
     * @param czpUserVOList 原始数组
     * @param creaters 创始人
     * @return 树型结构
     */
    public List<CzpUserVO> createCzpTree(List<CzpUserVO> czpUserVOList, List<String> creaters) {
        // 过滤根节点，并以父节点为分组条件
        Map<String, List<CzpUserVO>> czpMap = czpUserVOList.stream().filter(node -> !creaters.contains(node.getId()))
                .collect(Collectors.groupingBy(CzpUserVO::getParentId));
        // 为每个节点找到子节点
        czpUserVOList.forEach(node -> node.setChildren(czpMap.get(node.getId())));
        // 只留下根节点
        return czpUserVOList.stream().filter(node -> creaters.contains(node.getId())).collect(Collectors.toList());
    }



    /**
     * 这个方法要能递归查出所有族群的数据
     * @param subgroup 第一级子族群
     * @return 封装到只剩下第一级子族群的树型结构
     */
    public List<CzpUserVO> subgroupList(List<String> subgroup) {
        List<CzpUserVO> resultList = new ArrayList<>();
        //第一级子族群的数据
        if (subgroup.size() > 0) {
            for (String s : subgroup) {
                //查询子节点
                List<String> subgroup1 = czpGroupMapper.getSubgroup(s);
                if (subgroup1 != null && subgroup1.size() > 0) {
                    //还有子节点，先查子节点，并递归
                    resultList.addAll(subgroupList(subgroup1));
                }
                //到这里时，没有子节点，递归结束
                List<CzpUserVO> list = czpUserMapper.relationChat2Area(s);
                resultList.addAll(list);
            }
        } else {
            return null;
        }
        return resultList;
    }

}
