<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="pruebaMonoliticaApp.unidad.home.createOrEditLabel"
          data-cy="UnidadCreateUpdateHeading"
          v-text="$t('pruebaMonoliticaApp.unidad.home.createOrEditLabel')"
        >
          Create or edit a Unidad
        </h2>
        <div>
          <div class="form-group" v-if="unidad.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="unidad.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('pruebaMonoliticaApp.unidad.marca')" for="unidad-marca">Marca</label>
            <input
              type="text"
              class="form-control"
              name="marca"
              id="unidad-marca"
              data-cy="marca"
              :class="{ valid: !$v.unidad.marca.$invalid, invalid: $v.unidad.marca.$invalid }"
              v-model="$v.unidad.marca.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('pruebaMonoliticaApp.unidad.modelo')" for="unidad-modelo">Modelo</label>
            <input
              type="text"
              class="form-control"
              name="modelo"
              id="unidad-modelo"
              data-cy="modelo"
              :class="{ valid: !$v.unidad.modelo.$invalid, invalid: $v.unidad.modelo.$invalid }"
              v-model="$v.unidad.modelo.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('pruebaMonoliticaApp.unidad.matricula')" for="unidad-matricula">Matricula</label>
            <input
              type="text"
              class="form-control"
              name="matricula"
              id="unidad-matricula"
              data-cy="matricula"
              :class="{ valid: !$v.unidad.matricula.$invalid, invalid: $v.unidad.matricula.$invalid }"
              v-model="$v.unidad.matricula.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('pruebaMonoliticaApp.unidad.color')" for="unidad-color">Color</label>
            <input
              type="text"
              class="form-control"
              name="color"
              id="unidad-color"
              data-cy="color"
              :class="{ valid: !$v.unidad.color.$invalid, invalid: $v.unidad.color.$invalid }"
              v-model="$v.unidad.color.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('pruebaMonoliticaApp.unidad.numSerie')" for="unidad-numSerie">Num Serie</label>
            <input
              type="text"
              class="form-control"
              name="numSerie"
              id="unidad-numSerie"
              data-cy="numSerie"
              :class="{ valid: !$v.unidad.numSerie.$invalid, invalid: $v.unidad.numSerie.$invalid }"
              v-model="$v.unidad.numSerie.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('pruebaMonoliticaApp.unidad.generacion')" for="unidad-generacion"
              >Generacion</label
            >
            <input
              type="text"
              class="form-control"
              name="generacion"
              id="unidad-generacion"
              data-cy="generacion"
              :class="{ valid: !$v.unidad.generacion.$invalid, invalid: $v.unidad.generacion.$invalid }"
              v-model="$v.unidad.generacion.$model"
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
            :disabled="$v.unidad.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./unidad-update.component.ts"></script>
