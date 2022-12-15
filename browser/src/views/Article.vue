<template>
  <div>
    <div class="crumbs">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item>
          <i class="el-icon-lx-cascades"></i> 商品信息查看
        </el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div class="container">
      <div class="handle-box" style="padding-bottom: 10px">
        <el-input v-model="searchId" placeholder="查找商品" style="width: 200px; margin-right: 20px"></el-input>
        <el-button type="primary" icon="Search" @click="handleSearch">查找</el-button>
      </div>
      <el-table :data="tableData" border class="table" ref="multipleTable" header-cell-class-name="table-header">
        <el-table-column prop="articleId" label="ID"></el-table-column>
        <el-table-column prop="article" label="商品名"></el-table-column>
        <el-table-column prop="cost" label="价格"></el-table-column>
        <el-table-column prop="articleInf" label="介绍"></el-table-column>
      </el-table>
      <div class="pagination">
        <el-pagination
          background
          layout="total, prev, pager, next"
          :current-page="query.current"
          :page-size="query.size"
          :total="pageTotal"
          @current-change="handlePageChange"
        ></el-pagination>
      </div>
    </div>
  </div>
</template>

<script setup>
import {reactive, ref} from "vue";
import {findArticle, getAllArticle} from "@/api";
import {ElMessage} from "element-plus";

const searchId = ref()

const tableData = ref([]);
const query = reactive({
  current: 1,
  size: 10
});
const pageTotal = ref(0);
// 分页导航
const handlePageChange = (val) => {
  query.current = val;
  getData();
};

// 获取表格数据
const getData = () => {
  getAllArticle(query.current, query.size).then(res => {
    if (res.data.code === 10000) {
      tableData.value = res.data.data.records
      pageTotal.value = res.data.data.total
    } else {
      ElMessage.error(res.data.info);
    }
  }).catch(err => {
    console.error(err)
    ElMessage.error(err.message);
  })
}

getData();

const handleSearch = () => {
  if (searchId.value) {
    findArticle(searchId.value, 1, 10).then(res => {
      if (res.data.code === 10000) {
        query.current = 1
        tableData.value = res.data.data.records
        const total = res.data.data.total
        if (total < 10) {
          pageTotal.value = total
        } else {
          pageTotal.value = 10
        }
      } else {
        ElMessage.error("获取商品信息失败：" + res.data.info);
      }
    }).catch(err => {
      console.error(err)
      ElMessage.error("获取商品信息失败：" + err.message);
    })
  } else {
    getData()
  }
}
</script>

<style scoped>

</style>
