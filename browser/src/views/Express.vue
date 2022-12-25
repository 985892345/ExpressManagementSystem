<template>
  <div>
    <div class="crumbs">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item>
          <i class="el-icon-lx-cascades"></i> 快递信息查看
        </el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    <div class="container">
      <div class="handle-box" style="padding-bottom: 10px">
        <el-input v-model="searchId" placeholder="查找快递id(精确查询)" style="width: 200px; margin-right: 20px"></el-input>
        <el-button type="primary" icon="Search" @click="handleSearch">查找</el-button>
      </div>
      <el-table :data="tableData" border class="table" ref="multipleTable" header-cell-class-name="table-header">
        <el-table-column prop="expressId" label="快递id"></el-table-column>
        <el-table-column prop="articleId" label="商品id"></el-table-column>
        <el-table-column prop="cost" label="运送费用"></el-table-column>
        <el-table-column prop="sentId" label="发送人id"></el-table-column>
        <el-table-column prop="receiveId" label="收件人id"></el-table-column>
        <el-table-column prop="companyId" label="快递公司id"></el-table-column>
        <el-table-column prop="local" label="当前位置"></el-table-column>
        <el-table-column prop="origin" label="始发地"></el-table-column>
        <el-table-column prop="dest" label="目的地"></el-table-column>
        <el-table-column label="操作" width="220" align="center" :hidden="!accountStore.user.admin">
          <template #default="scope">
            <el-button text :icon="Edit" @click="handleEdit(scope.$index, scope.row)">
              修改
            </el-button>
            <el-button text :icon="Delete" @click="handleDelete(scope.row.expressId)">
              删除
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
        <el-button type="primary" icon="Plus" @click="handleAdd" :hidden="!accountStore.user.admin">添加</el-button>
      </div>
    </div>

    <!-- 编辑弹出框 -->
    <el-dialog title="编辑" v-model="editVisible" width="30%">
      <el-form label-width="70px">
        <el-form-item label="当前位置">
          <el-input v-model="form.local"></el-input>
        </el-form-item>
        <el-form-item label="目的地">
          <el-input v-model="form.dest"></el-input>
        </el-form-item>
        <el-form-item label="运送费用">
          <el-input v-model="form.cost" type="number"></el-input>
        </el-form-item>
      </el-form>
      <template #footer>
				<span class="dialog-footer">
					<el-button @click="editVisible = false">取 消</el-button>
					<el-button type="primary" @click="saveEdit">确 定</el-button>
				</span>
      </template>
    </el-dialog>

    <!-- 编辑弹出框 -->
    <el-dialog title="添加" v-model="addVisible" width="30%">
      <el-form label-width="100px">
        <el-form-item label="商品id">
          <el-input v-model="form.articleId"></el-input>
        </el-form-item>
        <el-form-item label="运送费用">
          <el-input v-model="form.cost" type="number" placeholder="元"></el-input>
        </el-form-item>
        <el-form-item label="发送人id">
          <el-input v-model="form.sentId" type="number"></el-input>
        </el-form-item>
        <el-form-item label="收件人id">
          <el-input v-model="form.receiveId" type="number"></el-input>
        </el-form-item>
        <el-form-item label="快递公司id">
          <el-input v-model="form.companyId" type="number"></el-input>
        </el-form-item>
        <el-form-item label="当前位置">
          <el-input v-model="form.local"></el-input>
        </el-form-item>
        <el-form-item label="始发地">
          <el-input v-model="form.origin"></el-input>
        </el-form-item>
        <el-form-item label="目的地">
          <el-input v-model="form.dest"></el-input>
        </el-form-item>
      </el-form>
      <template #footer>
				<span class="dialog-footer">
					<el-button @click="addVisible = false">取 消</el-button>
					<el-button type="primary" @click="saveAdd">确 定</el-button>
				</span>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import {reactive, ref} from "vue";
import {Delete, Edit} from "@element-plus/icons-vue";
import {addExpress, deleteExpress, findExpress, getAllExpress, updateExpress} from "@/api";
import {ElMessage} from "element-plus";
import {useAccountStore} from "@/store/account";

const accountStore = useAccountStore()

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
  getAllExpress(query.current, query.size).then(res => {
    if (res.data.code === 10000) {
      tableData.value = res.data.data.records
      pageTotal.value = res.data.data.total
    } else {
      ElMessage.error("获取快递信息失败：" + res.data.info);
    }
  }).catch(err => {
    console.error(err)
    ElMessage.error("获取快递信息失败：" + err.message);
  })
}

getData();

const handleSearch = () => {
  if (searchId.value) {
    findExpress(searchId.value).then(res => {
      if (res.data.code === 10000) {
        query.current = 1
        tableData.value = [res.data.data]
        pageTotal.value = 1
      } else if (res.data.code === 10020) {
        ElMessage.info(res.data.info)
      } else {
        ElMessage.error("获取快递信息失败：" + res.data.info);
      }
    }).catch(err => {
      console.error(err)
      ElMessage.error("获取快递信息失败：" + err.message);
    })
  } else {
    getData()
  }
}

// 表格编辑时弹窗和保存
const editVisible = ref(false)
let form = reactive({
  expressId: 0,
  articleId: '',
  cost: '',
  sentId: '',
  receiveId: '',
  companyId: '',
  local: '',
  origin: '',
  dest: ''
})
let idx = -1;
const handleEdit = (index, row) => {
  idx = index;
  form.expressId = row.expressId
  form.articleId = row.articleId
  form.cost = row.cost
  form.sentId = row.sentId
  form.receiveId = row.receiveId
  form.companyId = row.companyId
  form.local = row.local
  form.origin = row.origin
  form.dest = row.dest
  editVisible.value = true
}
const saveEdit = () => {
  updateExpress(form).then(res => {
    if (res.data.code === 10000) {
      ElMessage.success(`修改第 ${idx + 1} 行成功`);
      if (form.local !== '') {
        tableData.value[idx].local = form.local
      }
      if (form.dest !== '') {
        tableData.value[idx].dest = form.dest
      }
      if (form.cost !== '') {
        tableData.value[idx].cost = form.cost
      }
      editVisible.value = false
    } else {
      ElMessage.success(`修改失败` + res.data.info)
    }
  }).catch(err => {
    ElMessage.success(`修改失败` + err.message)
  })
}

const handleDelete = (expressId) => {
  deleteExpress(expressId).then(res => {
    if (res.data.code === 10000) {
      ElMessage.success("删除成功")
      getData()
    } else {
      ElMessage.error("删除失败：" + res.data.info)
    }
  }).catch(err => {
    ElMessage.error("删除失败：" + err.message)
  })
}

const addVisible = ref(false)
const handleAdd = () => {
  form.expressId = 0
  form.articleId = ''
  form.cost = ''
  form.sentId = ''
  form.receiveId = ''
  form.companyId = ''
  form.local = ''
  form.origin = ''
  form.dest = ''
  addVisible.value = true
}
const saveAdd = () => {
  addExpress(form).then(res => {
    if (res.data.code === 10000) {
      ElMessage.success("添加成功")
      query.current = 1
      getData()
      addVisible.value = false
    } else {
      ElMessage.error("添加失败：" + res.data.info)
    }
  }).catch(err => {
    ElMessage.error("添加失败：" + err.message)
  })
}
</script>

<style scoped>

</style>

