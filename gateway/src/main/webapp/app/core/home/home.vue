<template>
  <div class="home row">
    <div class="col-md-3">
      <span class="hipster img-fluid rounded"></span>
    </div>
    <div class="col-md-9">
      <h1 class="display-4">Java Hipster 에 오신 것을 환영합니다!</h1>
      <p class="lead">이것은 당신의 홈페이지입니다.</p>

      <div>
        <div class="alert alert-success" v-if="authenticated">
          <span v-if="username">"{{ username }}"(으)로 로그인하셨습니다.</span>
        </div>

        <div class="alert alert-warning" v-if="!authenticated">
          <span></span>
          <a class="alert-link" @click="openLogin()">인증</a
          ><span
            >을 원하시면, 기본 계정을 사용할 수 있습니다:<br />- 관리자 (아이디="admin", 비밀번호="admin") <br />- 사용자 (아이디="user",
            비밀번호="user").</span
          >
        </div>
        <div class="alert alert-warning" v-if="!authenticated">
          <span>아직 계정이 없습니까?</span>&nbsp;
          <router-link class="alert-link" to="/register">새로운 계정을 등록하세요</router-link>
        </div>
      </div>

      <p>JHipster에 질문이 있다면:</p>

      <ul>
        <li><a href="https://www.jhipster.tech/" target="_blank" rel="noopener noreferrer">JHipster 홈페이지</a></li>
        <li>
          <a href="https://stackoverflow.com/tags/jhipster/info" target="_blank" rel="noopener noreferrer">JHipster Stack Overflow</a>
        </li>
        <li>
          <a href="https://github.com/jhipster/generator-jhipster/issues?state=open" target="_blank" rel="noopener noreferrer"
            >JHipster 버그 트랙커</a
          >
        </li>
        <li>
          <a href="https://gitter.im/jhipster/generator-jhipster" target="_blank" rel="noopener noreferrer">JHipster public chat room</a>
        </li>
        <li>
          <a href="https://twitter.com/jhipster" target="_blank" rel="noopener noreferrer">트위터에서 @jhipster 에 접속하세요.</a>
        </li>
      </ul>

      <p>
        <span>JHipster 가 마음에 든다면, 별을 주는 것을 잊지 마세요.</span>
        <a href="https://github.com/jhipster/generator-jhipster" target="_blank" rel="noopener noreferrer">GitHub</a>!
      </p>
    </div>
  </div>
</template>

<script lang="ts" src="./home.component.ts"></script>
