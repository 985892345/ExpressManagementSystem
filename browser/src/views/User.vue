<template>
  <div>
    <div class="crumbs">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item>
          <i class="el-icon-lx-cascades"></i> 用户信息查看
        </el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div class="container">
      <div class="handle-box" style="padding-bottom: 10px">
        <el-input v-model="searchId" placeholder="查找用户id(精确查询)" style="width: 200px; margin-right: 20px"></el-input>
        <el-button type="primary" icon="Search" @click="handleSearch">查找</el-button>
      </div>
      <el-table :data="tableData" border class="table" ref="multipleTable" header-cell-class-name="table-header">
        <el-table-column prop="userId" label="用户id"></el-table-column>
        <el-table-column prop="username" label="用户名"></el-table-column>
        <el-table-column prop="address" label="地址"></el-table-column>
        <el-table-column prop="phone" label="手机号"></el-table-column>
        <el-table-column prop="sex" label="性别"></el-table-column>
        <el-table-column prop="admin" label="是否管理员"></el-table-column>
        <el-table-column label="操作" width="220" align="center">
          <template #default="scope">
            <el-button text :icon="Edit" @click="handleEdit(scope.$index, scope.row)">
              修改
            </el-button>
          </template>
        </el-table-column>
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

    <!-- 编辑弹出框 -->
    <el-dialog title="编辑" v-model="editVisible" width="30%">
      <el-form label-width="70px">
        <el-form-item label="地址">
          <el-input v-model="form.address"></el-input>
        </el-form-item>
        <el-form-item label="电话">
          <el-input v-model="form.phone" type="number"></el-input>
        </el-form-item>
        <el-form-item label="性别">
          <el-radio-group v-model="form.sex">
            <el-radio label="男">男</el-radio>
            <el-radio label="女">女</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="管理员">
          <el-radio-group v-model="form.admin">
            <el-radio label="1">true</el-radio>
            <el-radio label="0">false</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <template #footer>
				<span class="dialog-footer">
					<el-button @click="editVisible = false">取 消</el-button>
					<el-button type="primary" @click="saveEdit">确 定</el-button>
				</span>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import {reactive, ref} from "vue";
import {Edit} from "@element-plus/icons-vue";
import {updateUser, getUser, findUser} from "@/api";
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
  if (!searchId.value) {
    query.current = val;
    getData();
  }
};

// 获取表格数据
const getData = () => {
  getUser(query.current, query.size).then(res => {
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
    findUser(searchId.value).then(res => {
      if (res.data.code === 10000) {
        query.current = 1
        tableData.value = [res.data.data]
        pageTotal.value = 1
      } else {
        ElMessage.error("获取用户信息失败：" + res.data.info);
      }
    }).catch(err => {
      console.error(err)
      ElMessage.error("获取用户信息失败：" + err.message);
    })
  } else {
    getData()
  }
}

// 表格编辑时弹窗和保存
const editVisible = ref(false)
let form = reactive({
  userId: 0,
  address: '',
  phone: '',
  sex: '',
  admin: '',
})
let idx = -1;
const handleEdit = (index, row) => {
  idx = index;
  form.userId = row.userId
  form.address = row.address
  form.phone = row.phone
  form.sex = row.sex
  form.admin = row.admin
  editVisible.value = true
}
const saveEdit = () => {
  if (form.phone !== '' && form.phone.length !== 11) {
    ElMessage.warning("电话号码不正确!")
  } else {
    updateUser(form.userId, form.address, form.phone,form.sex, form.admin).then(res => {
      if (res.data.code === 10000) {
        ElMessage.success(`修改第 ${idx + 1} 行成功`);
        if (form.address !== "") {
          tableData.value[idx].address = form.address
        }
        if (form.phone !== "") {
          tableData.value[idx].phone = form.phone
        }
        if (form.sex !== "") {
          tableData.value[idx].avatar = form.avatar
        }
        if (form.admin !== "") {
          tableData.value[idx].admin = form.admin
        }
      } else {
        ElMessage.success(`修改失败` + res.data.info)
      }
      editVisible.value = false;
    }).catch(err => {
      ElMessage.error(err.message)
    })
  }
}
</script>

<style scoped>

</style>
