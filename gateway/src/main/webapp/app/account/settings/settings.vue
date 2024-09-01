<template>
  <div>
    <div class="row justify-content-center">
      <div class="col-md-8 toastify-container">
        <h2 v-if="username" id="settings-title">
          <span
            >[<strong>{{ username }}</strong
            >] 사용자 설정</span
          >
        </h2>

        <div class="alert alert-success" role="alert" v-if="success"><strong>사용자 설정 저장 완료!</strong></div>

        <div class="alert alert-danger" role="alert" v-if="errorEmailExists">
          <strong>이미 사용 중인 이메일입니다!</strong> 다른 이메일 주소를 선택하세요.
        </div>

        <form name="form" id="settings-form" @submit.prevent="save()" v-if="settingsAccount" novalidate>
          <div class="form-group">
            <label class="form-control-label" for="firstName">이름</label>
            <input
              type="text"
              class="form-control"
              id="firstName"
              name="firstName"
              placeholder="이름"
              :class="{ valid: !v$.settingsAccount.firstName.$invalid, invalid: v$.settingsAccount.firstName.$invalid }"
              v-model="v$.settingsAccount.firstName.$model"
              minlength="1"
              maxlength="50"
              required
              data-cy="firstname"
            />
            <div v-if="v$.settingsAccount.firstName.$anyDirty && v$.settingsAccount.firstName.$invalid">
              <small class="form-text text-danger" v-if="!v$.settingsAccount.firstName.required">이름을 입력해 주세요.</small>
              <small class="form-text text-danger" v-if="!v$.settingsAccount.firstName.minLength">이름은 최소 1자 이상이어야 합니다</small>
              <small class="form-text text-danger" v-if="!v$.settingsAccount.firstName.maxLength">이름은 최대 50자 까지입니다</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="lastName">성</label>
            <input
              type="text"
              class="form-control"
              id="lastName"
              name="lastName"
              placeholder="성"
              :class="{ valid: !v$.settingsAccount.lastName.$invalid, invalid: v$.settingsAccount.lastName.$invalid }"
              v-model="v$.settingsAccount.lastName.$model"
              minlength="1"
              maxlength="50"
              required
              data-cy="lastname"
            />
            <div v-if="v$.settingsAccount.lastName.$anyDirty && v$.settingsAccount.lastName.$invalid">
              <small class="form-text text-danger" v-if="!v$.settingsAccount.lastName.required">성을 입력해 주세요.</small>
              <small class="form-text text-danger" v-if="!v$.settingsAccount.lastName.minLength">성은 최소 1자 이상이어야 합니다</small>
              <small class="form-text text-danger" v-if="!v$.settingsAccount.lastName.maxLength">성은 최대 50자 까지입니다</small>
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="email">이메일</label>
            <input
              type="email"
              class="form-control"
              id="email"
              name="email"
              placeholder="이메일을 입력하세요"
              :class="{ valid: !v$.settingsAccount.email.$invalid, invalid: v$.settingsAccount.email.$invalid }"
              v-model="v$.settingsAccount.email.$model"
              minlength="5"
              maxlength="254"
              email
              required
              data-cy="email"
            />
            <div v-if="v$.settingsAccount.email.$anyDirty && v$.settingsAccount.email.$invalid">
              <small class="form-text text-danger" v-if="!v$.settingsAccount.email.required">이메일 입력이 필요합니다.</small>
              <small class="form-text text-danger" v-if="!v$.settingsAccount.email.email">잘못된 이메일입니다.</small>
              <small class="form-text text-danger" v-if="!v$.settingsAccount.email.minLength">이메일은 최소 5자 이상이어야 합니다</small>
              <small class="form-text text-danger" v-if="!v$.settingsAccount.email.maxLength">이메일은 최대 50자 까지입니다.</small>
            </div>
          </div>
          <button type="submit" :disabled="v$.settingsAccount.$invalid" class="btn btn-primary" data-cy="submit">저장</button>
        </form>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./settings.component.ts"></script>
