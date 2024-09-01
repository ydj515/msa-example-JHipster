<template>
  <div>
    <div class="row justify-content-center">
      <div class="col-md-8">
        <h1>계정 활성화</h1>
        <div class="alert alert-success" v-if="success">
          <span><strong>당신의 계정이 활성화되었습니다.</strong> </span>
          <a class="alert-link" @click="openLogin">인증</a>.
        </div>
        <div class="alert alert-danger" v-if="error">
          <strong>당신의 계정이 활성화될 수 없습니다.</strong> 회원가입을 위해 등록 폼을 이용해 주세요.
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./activate.component.ts"></script>
