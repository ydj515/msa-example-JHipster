<template>
  <div>
    <div class="row justify-content-center">
      <div class="col-md-8">
        <h1>비밀번호 변경</h1>

        <div class="alert alert-danger" v-if="keyMissing">변경키가 올바르지 않습니다.</div>

        <div class="alert alert-danger" v-if="error">
          <p>비밀번호를 변경할 수 없습니다. 비밀번호 변경 요청은 오직 24시간 내에만 유효함을 기억하세요.</p>
        </div>

        <div class="alert alert-success" v-if="success">
          <span><strong>비밀번호가 변경되었습니다.</strong> </span>
          <a class="alert-link" @click="openLogin">인증</a>
        </div>
        <div class="alert alert-danger" v-if="doNotMatch">
          <p>비밀번호가 일치하지 않습니다!</p>
        </div>

        <div class="alert alert-warning" v-if="!success && !keyMissing">
          <p>새 비밀번호를 선택하세요.</p>
        </div>

        <div v-if="!keyMissing">
          <form v-if="!success" name="form" @submit.prevent="finishReset()">
            <div class="form-group">
              <label class="form-control-label" for="newPassword">새 비밀번호</label>
              <input
                type="password"
                class="form-control"
                id="newPassword"
                name="newPassword"
                placeholder="새 비밀번호"
                :class="{ valid: !v$.resetAccount.newPassword.$invalid, invalid: v$.resetAccount.newPassword.$invalid }"
                v-model="v$.resetAccount.newPassword.$model"
                minlength="4"
                maxlength="50"
                required
                data-cy="resetPassword"
              />
              <div v-if="v$.resetAccount.newPassword.$anyDirty && v$.resetAccount.newPassword.$invalid">
                <small class="form-text text-danger" v-if="!v$.resetAccount.newPassword.required">비밀번호 입력이 필요합니다.</small>
                <small class="form-text text-danger" v-if="!v$.resetAccount.newPassword.minLength"
                  >비밀번호는 최소 4자 이상이어야 합니다</small
                >
                <small class="form-text text-danger" v-if="!v$.resetAccount.newPassword.maxLength">비밀번호는 최대 50자 까지입니다.</small>
              </div>
            </div>
            <div class="form-group">
              <label class="form-control-label" for="confirmPassword">새 비밀번호 확인</label>
              <input
                type="password"
                class="form-control"
                id="confirmPassword"
                name="confirmPassword"
                :class="{ valid: !v$.resetAccount.confirmPassword.$invalid, invalid: v$.resetAccount.confirmPassword.$invalid }"
                placeholder="새 비밀번호 확인"
                v-model="v$.resetAccount.confirmPassword.$model"
                minlength="4"
                maxlength="50"
                required
                data-cy="confirmResetPassword"
              />
              <div v-if="v$.resetAccount.confirmPassword.$anyDirty && v$.resetAccount.confirmPassword.$invalid">
                <small class="form-text text-danger" v-if="!v$.resetAccount.confirmPassword.sameAsPassword"
                  >비밀번호가 일치하지 않습니다!</small
                >
              </div>
            </div>
            <button type="submit" :disabled="v$.resetAccount.$invalid" class="btn btn-primary" data-cy="submit">저장</button>
          </form>
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./reset-password-finish.component.ts"></script>
