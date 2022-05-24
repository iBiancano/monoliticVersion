<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="taller">
        <h2 class="jh-entity-heading" data-cy="tallerDetailsHeading">
          <span v-text="$t('pruebaMonoliticaApp.taller.detail.title')">Taller</span> {{ taller.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('pruebaMonoliticaApp.taller.marca')">Marca</span>
          </dt>
          <dd>
            <span>{{ taller.marca }}</span>
          </dd>
          <dt>
            <span v-text="$t('pruebaMonoliticaApp.taller.modelo')">Modelo</span>
          </dt>
          <dd>
            <span>{{ taller.modelo }}</span>
          </dd>
          <dt>
            <span v-text="$t('pruebaMonoliticaApp.taller.matricula')">Matricula</span>
          </dt>
          <dd>
            <span>{{ taller.matricula }}</span>
          </dd>
          <dt>
            <span v-text="$t('pruebaMonoliticaApp.taller.color')">Color</span>
          </dt>
          <dd>
            <span>{{ taller.color }}</span>
          </dd>
          <dt>
            <span v-text="$t('pruebaMonoliticaApp.taller.numSerie')">Num Serie</span>
          </dt>
          <dd>
            <span>{{ taller.numSerie }}</span>
          </dd>
          <dt>
            <span v-text="$t('pruebaMonoliticaApp.taller.generacion')">Generacion</span>
          </dt>
          <dd>
            <span>{{ taller.generacion }}</span>
          </dd>
          <dt>
            <span v-text="$t('pruebaMonoliticaApp.taller.unidadservicio')">Unidadservicio</span>
          </dt>
          <dd>
            <div v-if="taller.unidadservicio">
              <router-link :to="{ name: 'UnidadServicioView', params: { unidadServicioId: taller.unidadservicio.id } }">{{
                taller.unidadservicio.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="taller.id" :to="{ name: 'TallerEdit', params: { tallerId: taller.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./taller-details.component.ts"></script>
