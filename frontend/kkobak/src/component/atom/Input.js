import React from "react";

import './Input.scss'

export default function Input({ type, placeholder, onChange, value }) {
  return (
    <label className="label">
      <input className="input" type={type} placeholder={placeholder} onChange={onChange} value={value} />
    </label>
  );
}
