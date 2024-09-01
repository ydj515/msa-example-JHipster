<template>
  <div>
    <div class="row justify-content-center">
      <div class="col-md-8 toastify-container">
        <h2 v-if="username" id="password-title">
          <span
            >[<strong>{{ username }}</strong
            >] 비밀번호</span
          >
        </h2>

        <div class="alert alert-success" role="alert" v-if="success"><strong>비밀번호 변경 완료!</strong></div>
        <div class="alert alert-danger" role="alert" v-if="error"><strong>에러가 발생했습니다!</strong> 비밀번호는 변경되지 않습니다.</div>

        <div class="alert alert-danger" role="alert" v-if="doNotMatch">비밀번호가 일치하지 않습니다!</div>

        <form name="form" id="password-form" @submit.prevent="changePassword()">
          <div class="form-group">
            <label class="form-control-label" for="currentPassword">Current password</label>
            <input
              type="password"
              class="form-control"
              id="currentPassword"
              name="currentPassword"
              :class="{ valid: !v$.resetPassword.currentPassword.$invalid, invalid: v$.resetPassword.currentPassword.$invalid }"
              placeholder="Current password"
              v-model="v$.resetPassword.currentPassword.$model"
              required
              data-cy="currentPassword"
            />
            <div v-if="v$.resetPassword.currentPassword.$anyDirty && v$.resetPassword.currentPassword.$invalid">
              <small class="form-text text-danger" v-if="!v$.resetPassword.currentPassword.required">비밀번호 입력이 필요합니다.</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="newPassword">새 비밀번호</label>
            <input
              type="password"
              class="form-control"
              id="newPassword"
              name="newPassword"
              placeholder="새 비밀번호"
              :class="{ valid: !v$.resetPassword.newPassword.$invalid, invalid: v$.resetPassword.newPassword.$invalid }"
              v-model="v$.resetPassword.newPassword.$model"
              minlength="4"
              maxlength="50"
              required
              data-cy="newPassword"
            />
            <div v-if="v$.resetPassword.newPassword.$anyDirty && v$.resetPassword.newPassword.$invalid">
              <small class="form-text text-danger" v-if="!v$.resetPassword.newPassword.required">비밀번호 입력이 필요합니다.</small>
              <small class="form-text text-danger" v-if="!v$.resetPassword.newPassword.minLength"
                >비밀번호는 최소 4자 이상이어야 합니다</small
              >
              <small class="form-text text-danger" v-if="!v$.resetPassword.newPassword.maxLength">비밀번호는 최대 50자 까지입니다.</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="confirmPassword">새 비밀번호 확인</label>
            <input
              type="password"
              class="form-control"
              id="confirmPassword"
              name="confirmPassword"
              :class="{ valid: !v$.resetPassword.confirmPassword.$invalid, invalid: v$.resetPassword.confirmPassword.$invalid }"
              placeholder="새 비밀번호 확인"
              v-model="v$.resetPassword.confirmPassword.$model"
              minlength="4"
              maxlength="50"
              required
              data-cy="confirmPassword"
            />
            <div v-if="v$.resetPassword.confirmPassword.$anyDirty && v$.resetPassword.confirmPassword.$invalid">
              <small class="form-text text-danger" v-if="!v$.resetPassword.confirmPassword.sameAsPassword"
                >비밀번호가 일치하지 않습니다!</small
              >
            </div>
          </div>

          <button type="submit" :disabled="v$.resetPassword.$invalid" class="btn btn-primary" data-cy="submit">저장</button>
        </form>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./change-password.component.ts"></script>
