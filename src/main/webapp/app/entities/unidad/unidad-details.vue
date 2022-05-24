<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="unidad">
        <h2 class="jh-entity-heading" data-cy="unidadDetailsHeading">
          <span v-text="$t('pruebaMonoliticaApp.unidad.detail.title')">Unidad</span> {{ unidad.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('pruebaMonoliticaApp.unidad.marca')">Marca</span>
          </dt>
          <dd>
            <span>{{ unidad.marca }}</span>
          </dd>
          <dt>
            <span v-text="$t('pruebaMonoliticaApp.unidad.modelo')">Modelo</span>
          </dt>
          <dd>
            <span>{{ unidad.modelo }}</span>
          </dd>
          <dt>
            <span v-text="$t('pruebaMonoliticaApp.unidad.matricula')">Matricula</span>
          </dt>
          <dd>
            <span>{{ unidad.matricula }}</span>
          </dd>
          <dt>
            <span v-text="$t('pruebaMonoliticaApp.unidad.color')">Color</span>
          </dt>
          <dd>
            <span>{{ unidad.color }}</span>
          </dd>
          <dt>
            <span v-text="$t('pruebaMonoliticaApp.unidad.numSerie')">Num Serie</span>
          </dt>
          <dd>
            <span>{{ unidad.numSerie }}</span>
          </dd>
          <dt>
            <span v-text="$t('pruebaMonoliticaApp.unidad.generacion')">Generacion</span>
          </dt>
          <dd>
            <span>{{ unidad.generacion }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="unidad.id" :to="{ name: 'UnidadEdit', params: { unidadId: unidad.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./unidad-details.component.ts"></script>
