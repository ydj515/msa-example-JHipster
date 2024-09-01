<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="user">
        <h2 class="jh-entity-heading">
          <span>사용자</span> [<strong>{{ user.login }}</strong
          >]
        </h2>
        <dl class="row jh-entity-details">
          <dt><span>로그인 아이디</span></dt>
          <dd>
            <span>{{ user.login }}</span>
            <b-badge variant="success" v-if="user.activated">활성</b-badge>
            <b-badge variant="danger" v-if="!user.activated">비활성</b-badge>
          </dd>
          <dt><span>이름</span></dt>
          <dd>{{ user.firstName }}</dd>
          <dt><span>성</span></dt>
          <dd>{{ user.lastName }}</dd>
          <dt><span>이메일</span></dt>
          <dd>{{ user.email }}</dd>
          <dt><span>생성자</span></dt>
          <dd>{{ user.createdBy }}</dd>
          <dt><span>생성일</span></dt>
          <dd>
            <span v-if="user.createdDate">
              {{ formatDate(user.createdDate) }}
            </span>
          </dd>
          <dt><span>수정자</span></dt>
          <dd>{{ user.lastModifiedBy }}</dd>
          <dt><span>수정일</span></dt>
          <dd>
            <span v-if="user.lastModifiedDate">
              {{ formatDate(user.lastModifiedDate) }}
            </span>
          </dd>
          <dt><span>Profiles</span></dt>
          <dd>
            <ul class="list-unstyled">
              <li v-for="authority of user.authorities" :key="authority">
                <b-badge variant="info">{{ authority }}</b-badge>
              </li>
            </ul>
          </dd>
        </dl>
        <router-link custom v-slot="{ navigate }" :to="{ name: 'JhiUser' }">
          <button @click="navigate" class="btn btn-info">
            <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span>뒤로</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./user-management-view.component.ts"></script>
