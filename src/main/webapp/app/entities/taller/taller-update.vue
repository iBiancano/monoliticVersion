<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="pruebaMonoliticaApp.taller.home.createOrEditLabel"
          data-cy="TallerCreateUpdateHeading"
          v-text="$t('pruebaMonoliticaApp.taller.home.createOrEditLabel')"
        >
          Create or edit a Taller
        </h2>
        <div>
          <div class="form-group" v-if="taller.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="taller.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('pruebaMonoliticaApp.taller.marca')" for="taller-marca">Marca</label>
            <input
              type="text"
              class="form-control"
              name="marca"
              id="taller-marca"
              data-cy="marca"
              :class="{ valid: !$v.taller.marca.$invalid, invalid: $v.taller.marca.$invalid }"
              v-model="$v.taller.marca.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('pruebaMonoliticaApp.taller.modelo')" for="taller-modelo">Modelo</label>
            <input
              type="text"
              class="form-control"
              name="modelo"
              id="taller-modelo"
              data-cy="modelo"
              :class="{ valid: !$v.taller.modelo.$invalid, invalid: $v.taller.modelo.$invalid }"
              v-model="$v.taller.modelo.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('pruebaMonoliticaApp.taller.matricula')" for="taller-matricula">Matricula</label>
            <input
              type="text"
              class="form-control"
              name="matricula"
              id="taller-matricula"
              data-cy="matricula"
              :class="{ valid: !$v.taller.matricula.$invalid, invalid: $v.taller.matricula.$invalid }"
              v-model="$v.taller.matricula.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('pruebaMonoliticaApp.taller.color')" for="taller-color">Color</label>
            <input
              type="text"
              class="form-control"
              name="color"
              id="taller-color"
              data-cy="color"
              :class="{ valid: !$v.taller.color.$invalid, invalid: $v.taller.color.$invalid }"
              v-model="$v.taller.color.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('pruebaMonoliticaApp.taller.numSerie')" for="taller-numSerie">Num Serie</label>
            <input
              type="text"
              class="form-control"
              name="numSerie"
              id="taller-numSerie"
              data-cy="numSerie"
              :class="{ valid: !$v.taller.numSerie.$invalid, invalid: $v.taller.numSerie.$invalid }"
              v-model="$v.taller.numSerie.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('pruebaMonoliticaApp.taller.generacion')" for="taller-generacion"
              >Generacion</label
            >
            <input
              type="text"
              class="form-control"
              name="generacion"
              id="taller-generacion"
              data-cy="generacion"
              :class="{ valid: !$v.taller.generacion.$invalid, invalid: $v.taller.generacion.$invalid }"
              v-model="$v.taller.generacion.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('pruebaMonoliticaApp.taller.unidadservicio')" for="taller-unidadservicio"
              >Unidadservicio</label
            >
            <select
              class="form-control"
              id="taller-unidadservicio"
              data-cy="unidadservicio"
              name="unidadservicio"
              v-model="taller.unidadservicio"
            >
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  taller.unidadservicio && unidadServicioOption.id === taller.unidadservicio.id
                    ? taller.unidadservicio
                    : unidadServicioOption
                "
                v-for="unidadServicioOption in unidadServicios"
                :key="unidadServicioOption.id"
              >
                {{ unidadServicioOption.id }}
              </option>
            </select>
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
            :disabled="$v.taller.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./taller-update.component.ts"></script>
