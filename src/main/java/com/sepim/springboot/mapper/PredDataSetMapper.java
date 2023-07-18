package com.sepim.springboot.mapper;

import com.sepim.springboot.entity.PredDataSet;
import com.sepim.springboot.entity.example.PredDataSetExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PredDataSetMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pred_data_set
     *
     * @mbg.generated
     */
    long countByExample(PredDataSetExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pred_data_set
     *
     * @mbg.generated
     */
    int deleteByExample(PredDataSetExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pred_data_set
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pred_data_set
     *
     * @mbg.generated
     */
    int insert(PredDataSet record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pred_data_set
     *
     * @mbg.generated
     */
    int insertSelective(PredDataSet record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pred_data_set
     *
     * @mbg.generated
     */
    List<PredDataSet> selectByExample(PredDataSetExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pred_data_set
     *
     * @mbg.generated
     */
    PredDataSet selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pred_data_set
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") PredDataSet record, @Param("example") PredDataSetExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pred_data_set
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") PredDataSet record, @Param("example") PredDataSetExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pred_data_set
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(PredDataSet record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table pred_data_set
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(PredDataSet record);
}