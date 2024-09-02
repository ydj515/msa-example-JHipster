<template>
  <div>
    <b-dropdown-item to="/book">
      <font-awesome-icon icon="asterisk" />
      <span>Book</span>
    </b-dropdown-item>
    <b-dropdown-item to="/book-catalog">
      <font-awesome-icon icon="asterisk" />
      <span>Book Catalog</span>
    </b-dropdown-item>
    <b-dropdown-item to="/rental">
      <font-awesome-icon icon="asterisk" />
      <span>Rental</span>
    </b-dropdown-item>
    <b-dropdown-item to="/rented-item">
      <font-awesome-icon icon="asterisk" />
      <span>Rented Item</span>
    </b-dropdown-item>
    <!-- jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here -->
  </div>
</template>

<script lang="ts" src="./entities-menu.component.ts"></script>
