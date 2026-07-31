// Small shield + flame mark echoing the WILDFIRE logo, rendered in the
// header on the orange bar (so it's drawn in white, not orange).
export default function BrandMark({ size = 28, color = '#ffffff' }) {
  return (
    <svg width={size} height={size} viewBox="0 0 100 120" fill="none" xmlns="http://www.w3.org/2000/svg">
      <path
        d="M50 2 L92 16 V60 C92 88 74 106 50 118 C26 106 8 88 8 60 V16 Z"
        fill={color}
        opacity="0.18"
      />
      <path
        d="M50 2 L92 16 V60 C92 88 74 106 50 118 C26 106 8 88 8 60 V16 Z"
        stroke={color}
        strokeWidth="4"
        fill="none"
      />
      <path
        d="M50 30 C56 42 46 46 50 56 C58 50 58 62 50 70 C40 76 34 66 38 56 C30 62 30 46 40 40 C36 34 44 24 50 30 Z"
        fill={color}
      />
    </svg>
  );
}
