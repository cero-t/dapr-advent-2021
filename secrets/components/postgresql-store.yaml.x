apiVersion: dapr.io/v1alpha1
kind: Component
metadata:
  name: postgresql-store
spec:
  type: state.postgresql
  version: v1
  metadata:
  - name: connectionString
    secretKeyRef:
      name: postgresql-secret
      key: connection
