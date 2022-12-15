export function setRefreshToken(refreshToken) {
  localStorage.setItem('refreshToken', refreshToken)
}

export function getRefreshToken() {
  return localStorage.getItem('refreshToken')
}

export function setToken(token) {
  localStorage.setItem('token', token)
}

export function getToken() {
  return localStorage.getItem('token')
}


