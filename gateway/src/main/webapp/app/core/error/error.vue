<template>
  <div>
    <div class="row">
      <div class="col-md-3">
        <span class="hipster img-fluid rounded"></span>
      </div>
      <div class="col-md-9">
        <h1>에러 페이지!</h1>

        <div v-if="errorMessage">
          <div class="alert alert-danger">{{ errorMessage }}</div>
        </div>
        <div v-if="error403" class="alert alert-danger">이 페이지에 접근할 권한이 없습니다.</div>
        <div v-if="error404" class="alert alert-warning">페이지가 존재하지 않습니다.</div>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./error.component.ts"></script>
