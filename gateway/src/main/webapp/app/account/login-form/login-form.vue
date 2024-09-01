<template>
  <div class="modal-body">
    <div class="row justify-content-center">
      <div class="col-md-8">
        <b-alert show data-cy="loginError" variant="danger" v-if="authenticationError"
          ><strong>인증 실패!</strong> credential을 확인하고 다시 시도해 주세요.</b-alert
        >
      </div>
      <div class="col-md-8">
        <b-form @submit.prevent="doLogin()">
          <b-form-group label="로그인 아이디" label-for="username">
            <b-form-input
              id="username"
              type="text"
              name="username"
              autofocus
              placeholder="로그인 아이디"
              v-model="login"
              data-cy="username"
            >
            </b-form-input>
          </b-form-group>
          <b-form-group label="비밀번호" label-for="password">
            <b-form-input id="password" type="password" name="password" placeholder="당신의 비밀번호" v-model="password" data-cy="password">
            </b-form-input>
          </b-form-group>
          <b-form-checkbox id="rememberMe" name="rememberMe" v-model="rememberMe" checked>
            <span>자동 로그인</span>
          </b-form-checkbox>
          <div>
            <b-button data-cy="submit" type="submit" variant="primary">인증하기</b-button>
          </div>
        </b-form>
        <p></p>
        <div>
          <b-alert show variant="warning">
            <b-link :to="'/account/reset/request'" class="alert-link" data-cy="forgetYourPasswordSelector">비밀번호를 잊으셨나요?</b-link>
          </b-alert>
        </div>
        <div>
          <b-alert show variant="warning">
            <span>아직 계정이 없습니까?</span>
            <b-link :to="'/register'" class="alert-link">새로운 계정을 등록하세요</b-link>
          </b-alert>
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./login-form.component.ts"></script>
