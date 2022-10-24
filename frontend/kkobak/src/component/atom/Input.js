import React from "react";

import './Input.scss'

export default function Input({ type, placeholder, onChange }) {
  return (
    <label className="label">
      <input className="input" type={type} placeholder={placeholder} onChange={onChange} />
    </label>
  );
}
