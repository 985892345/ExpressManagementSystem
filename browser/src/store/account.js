import {defineStore} from "pinia";
import {setRefreshToken, setToken} from "@/utils/token";

export const useAccountStore = defineStore('account', {
  state: ()=>{
    return {
      user: {
        userId: 0,
        username: '',
        address: '',
        phone: '',
        sex: '',
        admin: false,
      }
    }
  },
  getters: {},
  actions: {
    reset() {
      this.user = null
      setToken(null)
      setRefreshToken(null)
    },
    setUser(user) {
      this.user = user
    },
  }
})
