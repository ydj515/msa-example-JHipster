<template>
  <div>
    <div class="row justify-content-center">
      <div class="col-md-8 toastify-container">
        <h1 id="register-title" data-cy="registerTitle">등록</h1>

        <div class="alert alert-success" role="alert" v-if="success">
          <strong>회원가입 내용이 저장되었습니다!</strong> 가입을 완료하기 위해 이메일을 확인해주세요.
        </div>

        <div class="alert alert-danger" role="alert" v-if="error"><strong>회원가입이 실패했습니다!</strong> 다시 시도해 주세요.</div>

        <div class="alert alert-danger" role="alert" v-if="errorUserExists">
          <strong>로그인 아이디가 이미 존재합니다!</strong> 다른 아이디로 다시 시도해 주세요.
        </div>

        <div class="alert alert-danger" role="alert" v-if="errorEmailExists">
          <strong>이미 사용 중인 이메일입니다!</strong> 다른 이메일 주소를 선택하세요.
        </div>
      </div>
    </div>
    <div class="row justify-content-center">
      <div class="col-md-8">
        <form id="register-form" name="registerForm" @submit.prevent="register()" v-if="!success" no-validate>
          <div class="form-group">
            <label class="form-control-label" for="username">로그인 아이디</label>
            <input
              type="text"
              class="form-control"
              v-model="v$.registerAccount.login.$model"
              id="username"
              name="login"
              :class="{ valid: !v$.registerAccount.login.$invalid, invalid: v$.registerAccount.login.$invalid }"
              required
              minlength="1"
              maxlength="50"
              pattern="^[a-zA-Z0-9!#$&'*+=?^_`{|}~.-]+@?[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$"
              placeholder="로그인 아이디"
              data-cy="username"
            />
            <div v-if="v$.registerAccount.login.$anyDirty && v$.registerAccount.login.$invalid">
              <small class="form-text text-danger" v-if="!v$.registerAccount.login.required">아이디 입력이 필요합니다</small>
              <small class="form-text text-danger" v-if="!v$.registerAccount.login.minLength">아이디는 적어도 1자 이상이어야 합니다</small>
              <small class="form-text text-danger" v-if="!v$.registerAccount.login.maxLength">아이디는 최대 50자 까지입니다</small>
              <small class="form-text text-danger" v-if="!v$.registerAccount.login.pattern">Your username is invalid.</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="email">이메일</label>
            <input
              type="email"
              class="form-control"
              id="email"
              name="email"
              :class="{ valid: !v$.registerAccount.email.$invalid, invalid: v$.registerAccount.email.$invalid }"
              v-model="v$.registerAccount.email.$model"
              minlength="5"
              maxlength="254"
              email
              required
              placeholder="이메일을 입력하세요"
              data-cy="email"
            />
            <div v-if="v$.registerAccount.email.$anyDirty && v$.registerAccount.email.$invalid">
              <small class="form-text text-danger" v-if="!v$.registerAccount.email.required">이메일 입력이 필요합니다.</small>
              <small class="form-text text-danger" v-if="!v$.registerAccount.email.email">잘못된 이메일입니다.</small>
              <small class="form-text text-danger" v-if="!v$.registerAccount.email.minLength">이메일은 최소 5자 이상이어야 합니다</small>
              <small class="form-text text-danger" v-if="!v$.registerAccount.email.maxLength">이메일은 최대 50자 까지입니다.</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="firstPassword">새 비밀번호</label>
            <input
              type="password"
              class="form-control"
              id="firstPassword"
              name="password"
              :class="{ valid: !v$.registerAccount.password.$invalid, invalid: v$.registerAccount.password.$invalid }"
              v-model="v$.registerAccount.password.$model"
              minlength="4"
              maxlength="50"
              required
              placeholder="새 비밀번호"
              data-cy="firstPassword"
            />
            <div v-if="v$.registerAccount.password.$anyDirty && v$.registerAccount.password.$invalid">
              <small class="form-text text-danger" v-if="!v$.registerAccount.password.required">비밀번호 입력이 필요합니다.</small>
              <small class="form-text text-danger" v-if="!v$.registerAccount.password.minLength"
                >비밀번호는 최소 4자 이상이어야 합니다</small
              >
              <small class="form-text text-danger" v-if="!v$.registerAccount.password.maxLength">비밀번호는 최대 50자 까지입니다.</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="secondPassword">새 비밀번호 확인</label>
            <input
              type="password"
              class="form-control"
              id="secondPassword"
              name="confirmPasswordInput"
              :class="{ valid: !v$.confirmPassword.$invalid, invalid: v$.confirmPassword.$invalid }"
              v-model="v$.confirmPassword.$model"
              minlength="4"
              maxlength="50"
              required
              placeholder="새 비밀번호 확인"
              data-cy="secondPassword"
            />
            <div v-if="v$.confirmPassword.$dirty && v$.confirmPassword.$invalid">
              <small class="form-text text-danger" v-if="!v$.confirmPassword.required">확인할 비밀번호 입력이 필요합니다.</small>
              <small class="form-text text-danger" v-if="!v$.confirmPassword.minLength">확인할 비밀번호는 최소 4자 이상이어야 합니다</small>
              <small class="form-text text-danger" v-if="!v$.confirmPassword.maxLength">확인할 비밀번호는 최대 50자 까지입니다</small>
              <small class="form-text text-danger" v-if="!v$.confirmPassword.sameAsPassword">비밀번호가 일치하지 않습니다!</small>
            </div>
          </div>

          <button type="submit" :disabled="v$.$invalid" class="btn btn-primary" data-cy="submit">등록</button>
        </form>
        <p></p>
        <div class="alert alert-warning">
          <span></span>
          <a class="alert-link" @click="openLogin()">인증</a
          ><span
            >을 원하시면, 기본 계정을 사용할 수 있습니다:<br />- 관리자 (아이디="admin", 비밀번호="admin") <br />- 사용자 (아이디="user",
            비밀번호="user").</span
          >
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./register.component.ts"></script>
