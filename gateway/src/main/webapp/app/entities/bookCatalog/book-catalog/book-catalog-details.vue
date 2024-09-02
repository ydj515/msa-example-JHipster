<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="bookCatalog">
        <h2 class="jh-entity-heading" data-cy="bookCatalogDetailsHeading"><span>Book Catalog</span> {{ bookCatalog.id }}</h2>
        <dl class="row jh-entity-details">
          <dt>
            <span>Title</span>
          </dt>
          <dd>
            <span>{{ bookCatalog.title }}</span>
          </dd>
          <dt>
            <span>Author</span>
          </dt>
          <dd>
            <span>{{ bookCatalog.author }}</span>
          </dd>
          <dt>
            <span>Description</span>
          </dt>
          <dd>
            <span>{{ bookCatalog.description }}</span>
          </dd>
          <dt>
            <span>Book Id</span>
          </dt>
          <dd>
            <span>{{ bookCatalog.bookId }}</span>
          </dd>
          <dt>
            <span>Rent Cnt</span>
          </dt>
          <dd>
            <span>{{ bookCatalog.rentCnt }}</span>
          </dd>
        </dl>
        <button type="submit" @click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span>뒤로</span>
        </button>
        <router-link
          v-if="bookCatalog.id"
          :to="{ name: 'BookCatalogEdit', params: { bookCatalogId: bookCatalog.id } }"
          custom
          v-slot="{ navigate }"
        >
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span>수정</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./book-catalog-details.component.ts"></script>
