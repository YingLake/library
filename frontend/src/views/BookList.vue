<template>
  <div class="book-list-container">
    <div class="header">
      <h1>图书借阅管理系统</h1>
      <div class="user-info">
        <span>欢迎，{{ user.username }}</span>
        <el-button type="text" @click="handleLogout">退出</el-button>
      </div>
    </div>
    
    <div class="search-bar">
      <el-input 
        v-model="keyword" 
        placeholder="请输入书名搜索" 
        clearable
        class="search-input"
        @keyup.enter="loadBooks"
      />
      <el-button type="primary" @click="loadBooks">搜索</el-button>
    </div>
    
    <el-table :data="bookList" border style="width: 100%">
      <el-table-column prop="id" label="ID" width="80" />
      <el-table-column prop="name" label="书名" />
      <el-table-column prop="author" label="作者" />
      <el-table-column prop="status" label="状态" width="100">
        <template #default="scope">
          <el-tag :type="scope.row.status === 0 ? 'success' : 'warning'">
            {{ scope.row.status === 0 ? '可借' : '已借出' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="borrowTime" label="借阅时间" width="180">
        <template #default="scope">
          {{ scope.row.borrowTime ? formatDate(scope.row.borrowTime) : '-' }}
        </template>
      </el-table-column>
      <el-table-column label="操作" width="150">
        <template #default="scope">
          <el-button 
            v-if="scope.row.status === 0" 
            type="primary" 
            size="small"
            @click="handleBorrow(scope.row.id)"
          >
            借阅
          </el-button>
          <el-button 
            v-else-if="canReturn(scope.row)" 
            type="success" 
            size="small"
            @click="handleReturn(scope.row.id)"
          >
            归还
          </el-button>
          <span v-else class="no-permission">-</span>
        </template>
      </el-table-column>
    </el-table>
    
    <div class="pagination">
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="currentPage"
        :page-sizes="[5, 10, 20]"
        :page-size="pageSize"
        :total="total"
        layout="total, sizes, prev, pager, next, jumper"
      />
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import { useRouter } from 'vue-router'
import { getBookList, borrowBook, returnBook } from '../api'

const router = useRouter()
const user = ref(JSON.parse(localStorage.getItem('user') || '{}'))
const keyword = ref('')
const bookList = ref([])
const currentPage = ref(1)
const pageSize = ref(10)
const total = ref(0)

const formatDate = (dateStr) => {
  if (!dateStr) return '-'
  const date = new Date(dateStr)
  return date.toLocaleString('zh-CN')
}

const canReturn = (book) => {
  if (user.value.role === 1) return true
  return book.borrowerId === user.value.id
}

const loadBooks = async () => {
  try {
    const res = await getBookList({
      page: currentPage.value,
      size: pageSize.value,
      keyword: keyword.value
    })
    bookList.value = res.data.records
    total.value = res.data.total
  } catch (error) {
    console.error(error)
  }
}

const handleSizeChange = (size) => {
  pageSize.value = size
  currentPage.value = 1
  loadBooks()
}

const handleCurrentChange = (page) => {
  currentPage.value = page
  loadBooks()
}

const handleBorrow = async (bookId) => {
  try {
    await borrowBook(bookId)
    ElMessage.success('借阅成功')
    loadBooks()
  } catch (error) {
    console.error(error)
  }
}

const handleReturn = async (bookId) => {
  try {
    await returnBook(bookId)
    ElMessage.success('归还成功')
    loadBooks()
  } catch (error) {
    console.error(error)
  }
}

const handleLogout = () => {
  localStorage.removeItem('token')
  localStorage.removeItem('user')
  router.push('/login')
}

onMounted(() => {
  loadBooks()
})
</script>

<style scoped>
.book-list-container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 20px;
}

.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 30px;
  padding-bottom: 15px;
  border-bottom: 1px solid #e0e0e0;
}

.header h1 {
  color: #333;
  font-size: 24px;
}

.user-info {
  display: flex;
  align-items: center;
  gap: 15px;
}

.user-info span {
  color: #666;
}

.search-bar {
  display: flex;
  gap: 15px;
  margin-bottom: 20px;
}

.search-input {
  width: 300px;
}

.pagination {
  display: flex;
  justify-content: flex-end;
  margin-top: 20px;
}

.no-permission {
  color: #999;
  font-size: 14px;
}
</style>