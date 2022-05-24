<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="servicio">
        <h2 class="jh-entity-heading" data-cy="servicioDetailsHeading">
          <span v-text="$t('pruebaMonoliticaApp.servicio.detail.title')">Servicio</span> {{ servicio.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('pruebaMonoliticaApp.servicio.nombre')">Nombre</span>
          </dt>
          <dd>
            <span>{{ servicio.nombre }}</span>
          </dd>
          <dt>
            <span v-text="$t('pruebaMonoliticaApp.servicio.duracion')">Duracion</span>
          </dt>
          <dd>
            <span>{{ servicio.duracion }}</span>
          </dd>
          <dt>
            <span v-text="$t('pruebaMonoliticaApp.servicio.desc')">Desc</span>
          </dt>
          <dd>
            <span>{{ servicio.desc }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="servicio.id" :to="{ name: 'ServicioEdit', params: { servicioId: servicio.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./servicio-details.component.ts"></script>
