<template>
  <div>
    <div class="row justify-content-center">
      <div class="col-md-8">
        <h1>비밀번호 변경</h1>

        <div class="alert alert-warning" v-if="!success">
          <p>회원가입 시 사용한 이메일 주소를 입력하세요.</p>
        </div>

        <div class="alert alert-success" v-if="success">
          <p>비밀번호 변경 방법에 대한 자세한 내용은 이메일을 확인하세요.</p>
        </div>

        <form v-if="!success" name="form" @submit.prevent="requestReset()">
          <div class="form-group">
            <label class="form-control-label" for="email">이메일</label>
            <input
              type="email"
              class="form-control"
              id="email"
              name="email"
              placeholder="이메일을 입력하세요"
              :class="{ valid: !v$.resetAccount.email.$invalid, invalid: v$.resetAccount.email.$invalid }"
              v-model="v$.resetAccount.email.$model"
              minlength="5"
              maxlength="254"
              email
              required
              data-cy="emailResetPassword"
            />
            <div v-if="v$.resetAccount.email.$anyDirty && v$.resetAccount.email.$invalid">
              <small class="form-text text-danger" v-if="!v$.resetAccount.email.required">이메일 입력이 필요합니다.</small>
              <small class="form-text text-danger" v-if="!v$.resetAccount.email.email">잘못된 이메일입니다.</small>
              <small class="form-text text-danger" v-if="!v$.resetAccount.email.minLength">이메일은 최소 5자 이상이어야 합니다</small>
              <small class="form-text text-danger" v-if="!v$.resetAccount.email.maxLength">이메일은 최대 50자 까지입니다.</small>
            </div>
          </div>
          <button type="submit" :disabled="v$.resetAccount.$invalid" class="btn btn-primary" data-cy="submit">비밀번호 변경</button>
        </form>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./reset-password-init.component.ts"></script>
