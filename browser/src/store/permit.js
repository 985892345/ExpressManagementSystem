import { defineStore } from 'pinia';

export const usePermitStore = defineStore('permit', {
	state: () => {
		const keys = localStorage.getItem('ms_keys');
		return {
			key: keys ? JSON.parse(keys) : [],
			defaultList:{
				admin: ['1', '2', '3', '4'],
				user: ['2', '3', '4'],
			}
		};
	},
	actions: {
		handleSet(val) {
			this.key = val;
		}
	}
});
