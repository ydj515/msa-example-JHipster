<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" novalidate @submit.prevent="save()" v-if="userAccount">
        <h2 id="myUserLabel">사용자 생성 또는 수정</h2>
        <div>
          <div class="form-group" :hidden="!userAccount.id">
            <label>ID</label>
            <input type="text" class="form-control" name="id" v-model="userAccount.id" readonly />
          </div>

          <div class="form-group">
            <label class="form-control-label">로그인 아이디</label>
            <input
              type="text"
              class="form-control"
              name="login"
              :class="{ valid: !v$.userAccount.login.$invalid, invalid: v$.userAccount.login.$invalid }"
              v-model="v$.userAccount.login.$model"
            />

            <div v-if="v$.userAccount.login.$anyDirty && v$.userAccount.login.$invalid">
              <small class="form-text text-danger" v-if="!v$.userAccount.login.required">필수항목입니다.</small>

              <small class="form-text text-danger" v-if="!v$.userAccount.login.maxLength">최대 50자 이하까지만 입력 가능합니다.</small>

              <small class="form-text text-danger" v-if="!v$.userAccount.login.pattern"
                >This field can only contain letters, digits and e-mail addresses.</small
              >
            </div>
          </div>
          <div class="form-group">
            <label class="form-control-label" for="firstName">이름</label>
            <input
              type="text"
              class="form-control"
              id="firstName"
              name="firstName"
              placeholder="이름"
              :class="{ valid: !v$.userAccount.firstName.$invalid, invalid: v$.userAccount.firstName.$invalid }"
              v-model="v$.userAccount.firstName.$model"
            />
            <div v-if="v$.userAccount.firstName.$anyDirty && v$.userAccount.firstName.$invalid">
              <small class="form-text text-danger" v-if="!v$.userAccount.firstName.maxLength">최대 50자 이하까지만 입력 가능합니다.</small>
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
              :class="{ valid: !v$.userAccount.lastName.$invalid, invalid: v$.userAccount.lastName.$invalid }"
              v-model="v$.userAccount.lastName.$model"
            />
            <div v-if="v$.userAccount.lastName.$anyDirty && v$.userAccount.lastName.$invalid">
              <small class="form-text text-danger" v-if="!v$.userAccount.lastName.maxLength">최대 50자 이하까지만 입력 가능합니다.</small>
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
              :class="{ valid: !v$.userAccount.email.$invalid, invalid: v$.userAccount.email.$invalid }"
              v-model="v$.userAccount.email.$model"
              email
              required
            />
            <div v-if="v$.userAccount.email.$anyDirty && v$.userAccount.email.$invalid">
              <small class="form-text text-danger" v-if="!v$.userAccount.email.required">이메일 입력이 필요합니다.</small>
              <small class="form-text text-danger" v-if="!v$.userAccount.email.email">잘못된 이메일입니다.</small>
              <small class="form-text text-danger" v-if="!v$.userAccount.email.minLength">이메일은 최소 5자 이상이어야 합니다</small>
              <small class="form-text text-danger" v-if="!v$.userAccount.email.maxLength">이메일은 최대 50자 까지입니다.</small>
            </div>
          </div>
          <div class="form-check">
            <label class="form-check-label" for="activated">
              <input
                class="form-check-input"
                :disabled="userAccount.id === null"
                type="checkbox"
                id="activated"
                name="activated"
                v-model="userAccount.activated"
              />
              <span>활성</span>
            </label>
          </div>

          <div class="form-group">
            <label>Profiles</label>
            <select class="form-control" multiple name="authority" v-model="userAccount.authorities">
              <option v-for="authority of authorities" :value="authority" :key="authority">{{ authority }}</option>
            </select>
          </div>
        </div>
        <div>
          <button type="button" class="btn btn-secondary" @click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span>취소</span>
          </button>
          <button type="submit" :disabled="v$.userAccount.$invalid || isSaving" class="btn btn-primary">
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span>저장</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>

<script lang="ts" src="./user-management-edit.component.ts"></script>
