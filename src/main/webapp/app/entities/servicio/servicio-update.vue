<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="pruebaMonoliticaApp.servicio.home.createOrEditLabel"
          data-cy="ServicioCreateUpdateHeading"
          v-text="$t('pruebaMonoliticaApp.servicio.home.createOrEditLabel')"
        >
          Create or edit a Servicio
        </h2>
        <div>
          <div class="form-group" v-if="servicio.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="servicio.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('pruebaMonoliticaApp.servicio.nombre')" for="servicio-nombre">Nombre</label>
            <input
              type="text"
              class="form-control"
              name="nombre"
              id="servicio-nombre"
              data-cy="nombre"
              :class="{ valid: !$v.servicio.nombre.$invalid, invalid: $v.servicio.nombre.$invalid }"
              v-model="$v.servicio.nombre.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('pruebaMonoliticaApp.servicio.duracion')" for="servicio-duracion">Duracion</label>
            <input
              type="text"
              class="form-control"
              name="duracion"
              id="servicio-duracion"
              data-cy="duracion"
              :class="{ valid: !$v.servicio.duracion.$invalid, invalid: $v.servicio.duracion.$invalid }"
              v-model="$v.servicio.duracion.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('pruebaMonoliticaApp.servicio.desc')" for="servicio-desc">Desc</label>
            <input
              type="text"
              class="form-control"
              name="desc"
              id="servicio-desc"
              data-cy="desc"
              :class="{ valid: !$v.servicio.desc.$invalid, invalid: $v.servicio.desc.$invalid }"
              v-model="$v.servicio.desc.$model"
            />
          </div>
        </div>
        <div>
          <button type="button" id="cancel-save" data-cy="entityCreateCancelButton" class="btn btn-secondary" v-on:click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
          </button>
          <button
            type="submit"
            id="save-entity"
            data-cy="entityCreateSaveButton"
            :disabled="$v.servicio.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./servicio-update.component.ts"></script>
